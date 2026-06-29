package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResponseDto(
        Long id,
        String code,
        String phoneNumber,
        String email,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt
) {}
