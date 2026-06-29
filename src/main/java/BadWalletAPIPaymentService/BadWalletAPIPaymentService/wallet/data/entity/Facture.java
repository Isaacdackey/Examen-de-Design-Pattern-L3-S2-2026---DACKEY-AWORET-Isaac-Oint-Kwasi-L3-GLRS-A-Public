package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "factures")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String reference;


    private String walletCode;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    private boolean payee;

    @Column(name = "date_facture")
    private LocalDate dateFacture;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
