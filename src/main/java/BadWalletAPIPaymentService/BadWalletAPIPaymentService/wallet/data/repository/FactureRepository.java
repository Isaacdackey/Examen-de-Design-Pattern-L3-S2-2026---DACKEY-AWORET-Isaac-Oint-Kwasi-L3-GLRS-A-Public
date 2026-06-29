package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    Optional<Facture> findByReference(String reference);

    List<Facture> findByWalletCodeAndPayeeFalseAndDateFactureBetween(
            String walletCode, LocalDate debut, LocalDate fin);

    List<Facture> findByWalletCodeAndPayeeFalseAndDateFactureBetweenAndServiceName(
            String walletCode, LocalDate debut, LocalDate fin, String serviceName);

    List<Facture> findByWalletCodeAndPayeeFalse(String walletCode);

    List<Facture> findByReferenceIn(List<String> references);

    @Query("SELECT f FROM Facture f WHERE f.walletCode = :walletCode AND f.payee = false " +
           "AND YEAR(f.dateFacture) = YEAR(CURRENT_DATE) AND MONTH(f.dateFacture) = MONTH(CURRENT_DATE)")
    List<Facture> findFacturesCurrentMonth(String walletCode);

    @Query("SELECT f FROM Facture f WHERE f.walletCode = :walletCode AND f.payee = false " +
           "AND YEAR(f.dateFacture) = YEAR(CURRENT_DATE) AND MONTH(f.dateFacture) = MONTH(CURRENT_DATE) " +
           "AND f.serviceName = :serviceName")
    List<Facture> findFacturesCurrentMonthByService(String walletCode, String serviceName);
}
