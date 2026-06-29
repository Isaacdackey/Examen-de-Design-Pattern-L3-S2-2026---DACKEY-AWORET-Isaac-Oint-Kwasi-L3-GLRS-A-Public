package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequestDto(
        @NotBlank(message = "Le numéro de l'expéditeur est requis")
        String senderPhone,

        @NotBlank(message = "Le numéro du destinataire est requis")
        String receiverPhone,

        @NotNull @DecimalMin(value = "1.0", message = "Le montant doit être supérieur à 0")
        BigDecimal amount
) {}
