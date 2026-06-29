package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PayFacturesRequestDto(
        @NotBlank String phoneNumber,
        @NotBlank String serviceName,
        @NotEmpty List<String> factureReferences
) {}
