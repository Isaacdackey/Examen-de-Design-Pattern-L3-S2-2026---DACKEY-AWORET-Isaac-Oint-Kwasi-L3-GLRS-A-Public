package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Transaction;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.TransactionType;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionFactory {


    public Transaction createDepositTransaction(Wallet wallet, BigDecimal amount, String description) {
        return Transaction.builder()
                .wallet(wallet)
                .type(TransactionType.DEPOT)
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .description(description)
                .build();
    }


    public Transaction createWithdrawTransaction(Wallet wallet, BigDecimal amount, BigDecimal fees) {
        return Transaction.builder()
                .wallet(wallet)
                .type(TransactionType.RETRAIT)
                .amount(amount)
                .fees(fees)
                .description("Retrait - Frais: " + fees + " XOF")
                .build();
    }


    public Transaction createTransferSendTransaction(Wallet sender, BigDecimal amount, String receiverPhone) {
        return Transaction.builder()
                .wallet(sender)
                .type(TransactionType.TRANSFERT_ENVOI)
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .description("Transfert vers " + receiverPhone)
                .targetPhone(receiverPhone)
                .build();
    }


    public Transaction createTransferReceiveTransaction(Wallet receiver, BigDecimal amount, String senderPhone) {
        return Transaction.builder()
                .wallet(receiver)
                .type(TransactionType.TRANSFERT_RECEPTION)
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .description("Transfert reçu de " + senderPhone)
                .targetPhone(senderPhone)
                .build();
    }


    public Transaction createPaymentTransaction(Wallet wallet, BigDecimal amount, String description) {
        return Transaction.builder()
                .wallet(wallet)
                .type(TransactionType.PAIEMENT_FACTURE)
                .amount(amount)
                .fees(BigDecimal.ZERO)
                .description(description)
                .build();
    }
}