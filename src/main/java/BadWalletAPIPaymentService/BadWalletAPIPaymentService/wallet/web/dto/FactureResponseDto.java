package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FactureResponseDto(
        Long id,
        String reference,
        String walletCode,
        String serviceName,
        BigDecimal montant,
        boolean payee,
        LocalDate dateFacture,
        LocalDateTime datePaiement
) {}
