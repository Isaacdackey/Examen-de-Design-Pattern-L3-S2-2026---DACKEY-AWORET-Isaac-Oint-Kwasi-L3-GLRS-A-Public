package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.external.service.PaymentGatewayService;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.exceptions.EntityExistException;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.exceptions.EntityNotFoundException;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.exceptions.InsufficientFundsException;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Facture;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Transaction;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository.WalletRepository;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory.WalletFactory;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.*;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.mapper.WalletMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;
    private final FactureService factureService;
    private final PaymentGatewayService paymentGatewayService;
    private final WalletFactory walletFactory;

    @Value("${wallet.withdraw.fee.rate:0.01}")
    private double feeRate;

    @Value("${wallet.withdraw.fee.max:5000}")
    private double feeMax;

    public WalletServiceImpl(WalletRepository walletRepository,
                             TransactionService transactionService,
                             FactureService factureService,
                             PaymentGatewayService paymentGatewayService,
                             WalletFactory walletFactory) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
        this.factureService = factureService;
        this.paymentGatewayService = paymentGatewayService;
        this.walletFactory = walletFactory;
    }



    @Override
    public WalletResponseDto createWallet(WalletCreateRequestDto dto) {
        if (walletRepository.existsByPhoneNumber(dto.phoneNumber())) {
            throw new EntityExistException("Un portefeuille avec ce numéro existe déjà: " + dto.phoneNumber());
        }
        if (walletRepository.existsByEmail(dto.email())) {
            throw new EntityExistException("Un portefeuille avec cet email existe déjà: " + dto.email());
        }

        Wallet wallet = walletFactory.createWalletWithCurrency(
                dto.phoneNumber(),
                dto.email(),
                dto.code(),
                dto.initialBalance(),
                dto.currency()
        );
        walletRepository.save(wallet);
        return WalletMapper.toDto(wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletResponseDto> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable).map(WalletMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponseDto getWalletByPhone(String phoneNumber) {
        return WalletMapper.toDto(findWalletByPhone(phoneNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(String phoneNumber) {
        return findWalletByPhone(phoneNumber).getBalance();
    }

    private Wallet findWalletByPhone(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Portefeuille introuvable pour le numéro: " + phoneNumber));
    }

    @Override
    public TransactionResponseDto deposit(Long walletId, DepositRequestDto dto) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Portefeuille introuvable avec l'id: " + walletId));

        if ("CREDIT_CARD".equalsIgnoreCase(dto.paymentMethod())) {
            paymentGatewayService.processStripePayment(dto.amount());
        }

        wallet.setBalance(wallet.getBalance().add(dto.amount()));
        walletRepository.save(wallet);

        String desc = "Dépôt via " + (dto.paymentMethod() != null ? dto.paymentMethod() : "CASH");
        Transaction tx = transactionService.saveDepot(wallet, dto.amount(), desc);
        return WalletMapper.toTransactionDto(tx);
    }

    @Override
    public TransactionResponseDto withdraw(WithdrawRequestDto dto) {
        Wallet wallet = findWalletByPhone(dto.phoneNumber());

        BigDecimal fees = dto.amount().multiply(BigDecimal.valueOf(feeRate));
        BigDecimal maxFees = BigDecimal.valueOf(feeMax);
        if (fees.compareTo(maxFees) > 0) fees = maxFees;
        fees = fees.setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDebit = dto.amount().add(fees);
        if (wallet.getBalance().compareTo(totalDebit) < 0) {
            throw new InsufficientFundsException(
                    "Solde insuffisant. Solde: " + wallet.getBalance() + " XOF, Requis: " + totalDebit + " XOF (dont " + fees + " XOF de frais)");
        }

        wallet.setBalance(wallet.getBalance().subtract(totalDebit));
        walletRepository.save(wallet);

        Transaction tx = transactionService.saveRetrait(wallet, dto.amount(), fees);
        return WalletMapper.toTransactionDto(tx);
    }

    @Override
    public TransactionResponseDto transfer(TransferRequestDto dto) {
        if (dto.senderPhone().equals(dto.receiverPhone())) {
            throw new IllegalArgumentException("L'expéditeur et le destinataire ne peuvent pas être identiques");
        }
        Wallet sender = findWalletByPhone(dto.senderPhone());
        Wallet receiver = findWalletByPhone(dto.receiverPhone());

        if (sender.getBalance().compareTo(dto.amount()) < 0) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le transfert");
        }

        sender.setBalance(sender.getBalance().subtract(dto.amount()));
        receiver.setBalance(receiver.getBalance().add(dto.amount()));
        walletRepository.save(sender);
        walletRepository.save(receiver);

        Transaction tx = transactionService.saveTransfertEnvoi(sender, dto.amount(), dto.receiverPhone());
        transactionService.saveTransfertReception(receiver, dto.amount(), dto.senderPhone());
        return WalletMapper.toTransactionDto(tx);
    }

}