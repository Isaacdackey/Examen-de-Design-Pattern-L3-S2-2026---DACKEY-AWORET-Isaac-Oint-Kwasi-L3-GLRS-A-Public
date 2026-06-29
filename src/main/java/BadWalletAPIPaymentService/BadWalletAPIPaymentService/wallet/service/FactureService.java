package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Facture;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;

import java.time.LocalDate;
import java.util.List;

public interface FactureService {
    List<Facture> getFacturesDuMoisCourant(String walletCode, String unite);
    List<Facture> getFacturesSurPeriode(String walletCode, LocalDate debut, LocalDate fin);
    List<Facture> findByReferences(List<String> references);
    void seedFactures(Wallet wallet, int walletIndex);
}
