package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletFactory {


    public Wallet createWallet(String phoneNumber, String email, String code, BigDecimal balance) {
        return Wallet.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .code(code)
                .balance(balance)
                .currency("XOF")
                .build();
    }


    public Wallet createWalletWithCurrency(String phoneNumber, String email, String code,
                                           BigDecimal balance, String currency) {
        return Wallet.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .code(code)
                .balance(balance)
                .currency(currency != null ? currency : "XOF")
                .build();
    }


    public Wallet createSeedWallet(String phoneNumber, String email, String code, String balanceStr) {
        return Wallet.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .code(code)
                .balance(new BigDecimal(balanceStr))
                .currency("XOF")
                .build();
    }
}