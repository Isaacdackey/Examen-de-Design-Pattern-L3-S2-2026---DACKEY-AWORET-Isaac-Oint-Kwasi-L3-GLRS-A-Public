package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDto(
        Long id,
        TransactionType type,
        BigDecimal amount,
        BigDecimal fees,
        String description,
        String targetPhone,
        LocalDateTime createdAt
) {}
