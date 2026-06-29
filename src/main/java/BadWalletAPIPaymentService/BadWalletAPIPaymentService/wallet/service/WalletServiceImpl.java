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






}