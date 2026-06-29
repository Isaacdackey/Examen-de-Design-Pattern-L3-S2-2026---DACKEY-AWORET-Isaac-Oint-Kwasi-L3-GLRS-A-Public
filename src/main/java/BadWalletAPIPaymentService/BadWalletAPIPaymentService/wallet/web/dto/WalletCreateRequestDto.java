package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletCreateRequestDto(
        @NotBlank(message = "Le numéro de téléphone est requis")
        String phoneNumber,

        @NotBlank @Email(message = "Email invalide")
        String email,

        @NotNull @DecimalMin(value = "0.0", message = "Le solde initial ne peut pas être négatif")
        BigDecimal initialBalance,

        @NotBlank(message = "Le code est requis")
        String code,

        String currency
) {}
