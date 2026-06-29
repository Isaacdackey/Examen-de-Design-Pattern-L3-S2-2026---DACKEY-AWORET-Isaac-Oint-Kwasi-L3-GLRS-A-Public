package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Facture;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository.FactureRepository;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory.FactureFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;
    private final FactureFactory factureFactory;

    public FactureServiceImpl(FactureRepository factureRepository, FactureFactory factureFactory) {
        this.factureRepository = factureRepository;
        this.factureFactory = factureFactory;
    }

    @Override
    public List<Facture> getFacturesDuMoisCourant(String walletCode, String unite) {
        if (unite != null && !unite.isBlank()) {
            return factureRepository.findFacturesCurrentMonthByService(walletCode, unite);
        }
        return factureRepository.findFacturesCurrentMonth(walletCode);
    }

    @Override
    public List<Facture> getFacturesSurPeriode(String walletCode, LocalDate debut, LocalDate fin) {
        return factureRepository.findByWalletCodeAndPayeeFalseAndDateFactureBetween(walletCode, debut, fin);
    }

    @Override
    public List<Facture> findByReferences(List<String> references) {
        return factureRepository.findByReferenceIn(references);
    }

    @Override
    @Transactional
    public void seedFactures(Wallet wallet, int walletIndex) {
        String[] services = {"ISM", "WOYAFAL", "RAPIDO"};
        int k = 1;
        for (String svc : services) {
            for (int m = 1; m <= 2; m++) {
                String ref = "FAC-" + svc + "-" + walletIndex + "-" + k++;
                BigDecimal montant = BigDecimal.valueOf(5000 + m * 1000L);

                Facture facture = factureFactory.createCurrentMonthFacture(
                        wallet, svc, montant, ref
                );
                factureRepository.save(facture);
            }

            String ref = "FAC-" + svc + "-" + walletIndex + "-" + k++;
            BigDecimal montant = BigDecimal.valueOf(7500);

            Facture facture = factureFactory.createPastMonthFacture(
                    wallet, svc, montant, ref, 1
            );
            factureRepository.save(facture);
        }
    }
}