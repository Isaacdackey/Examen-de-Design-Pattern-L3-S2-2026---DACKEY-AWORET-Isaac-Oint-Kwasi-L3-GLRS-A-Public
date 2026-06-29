package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Transaction;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction saveDepot(Wallet wallet, BigDecimal amount, String description);
    Transaction saveRetrait(Wallet wallet, BigDecimal amount, BigDecimal fees);
    Transaction saveTransfertEnvoi(Wallet sender, BigDecimal amount, String receiverPhone);
    Transaction saveTransfertReception(Wallet receiver, BigDecimal amount, String senderPhone);
    Transaction savePaiement(Wallet wallet, BigDecimal amount, String description);
    List<TransactionResponseDto> getHistory(String phoneNumber);
}
