package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Transaction;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository.TransactionRepository;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory.TransactionFactory;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.TransactionResponseDto;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.mapper.WalletMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionFactory transactionFactory;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionFactory transactionFactory) {
        this.transactionRepository = transactionRepository;
        this.transactionFactory = transactionFactory;
    }

    @Override
    public Transaction saveDepot(Wallet wallet, BigDecimal amount, String description) {
        Transaction transaction = transactionFactory.createDepositTransaction(wallet, amount, description);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction saveRetrait(Wallet wallet, BigDecimal amount, BigDecimal fees) {
        Transaction transaction = transactionFactory.createWithdrawTransaction(wallet, amount, fees);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction saveTransfertEnvoi(Wallet sender, BigDecimal amount, String receiverPhone) {
        Transaction transaction = transactionFactory.createTransferSendTransaction(sender, amount, receiverPhone);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction saveTransfertReception(Wallet receiver, BigDecimal amount, String senderPhone) {
        Transaction transaction = transactionFactory.createTransferReceiveTransaction(receiver, amount, senderPhone);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction savePaiement(Wallet wallet, BigDecimal amount, String description) {
        Transaction transaction = transactionFactory.createPaymentTransaction(wallet, amount, description);
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getHistory(String phoneNumber) {
        return transactionRepository
                .findByWalletPhoneNumberOrderByCreatedAtDesc(phoneNumber)
                .stream()
                .map(WalletMapper::toTransactionDto)
                .toList();
    }
}