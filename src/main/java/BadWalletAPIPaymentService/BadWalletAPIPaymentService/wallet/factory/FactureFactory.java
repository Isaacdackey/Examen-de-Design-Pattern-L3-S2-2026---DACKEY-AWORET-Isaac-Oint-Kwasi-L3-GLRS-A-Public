package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Facture;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class FactureFactory {

    public Facture createFacture(Wallet wallet, String serviceName, BigDecimal montant, LocalDate date) {
        return Facture.builder()
                .walletCode(wallet.getCode())
                .serviceName(serviceName)
                .montant(montant)
                .payee(false)
                .dateFacture(date)
                .build();
    }

    public Facture createFactureWithReference(Wallet wallet, String serviceName, BigDecimal montant,
                                              LocalDate date, String reference) {
        return Facture.builder()
                .reference(reference)
                .walletCode(wallet.getCode())
                .serviceName(serviceName)
                .montant(montant)
                .payee(false)
                .dateFacture(date)
                .build();
    }


    public Facture createCurrentMonthFacture(Wallet wallet, String serviceName, BigDecimal montant, String reference) {
        return Facture.builder()
                .reference(reference)
                .walletCode(wallet.getCode())
                .serviceName(serviceName)
                .montant(montant)
                .payee(false)
                .dateFacture(LocalDate.now())
                .build();
    }


    public Facture createPastMonthFacture(Wallet wallet, String serviceName, BigDecimal montant,
                                          String reference, int monthsAgo) {
        return Facture.builder()
                .reference(reference)
                .walletCode(wallet.getCode())
                .serviceName(serviceName)
                .montant(montant)
                .payee(false)
                .dateFacture(LocalDate.now().minusMonths(monthsAgo))
                .build();
    }

    public Facture createPaidFacture(Wallet wallet, String serviceName, BigDecimal montant,
                                     LocalDate date, String reference) {
        return Facture.builder()
                .reference(reference)
                .walletCode(wallet.getCode())
                .serviceName(serviceName)
                .montant(montant)
                .payee(true)
                .dateFacture(date)
                .build();
    }
}