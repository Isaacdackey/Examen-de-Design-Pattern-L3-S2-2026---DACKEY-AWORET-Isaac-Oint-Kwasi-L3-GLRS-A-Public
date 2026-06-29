package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DepositRequestDto(
        @NotNull @DecimalMin(value = "1.0", message = "Le montant doit être supérieur à 0")
        BigDecimal amount,
        String paymentMethod
) {}
