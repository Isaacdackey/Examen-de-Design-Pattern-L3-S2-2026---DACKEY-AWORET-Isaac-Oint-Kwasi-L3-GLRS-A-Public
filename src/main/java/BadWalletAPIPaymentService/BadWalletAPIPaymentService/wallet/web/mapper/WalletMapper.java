package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.mapper;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Transaction;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.TransactionResponseDto;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.WalletCreateRequestDto;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.WalletResponseDto;

public final class WalletMapper {

    private WalletMapper() {}

    public static Wallet toEntity(WalletCreateRequestDto dto) {
        return Wallet.builder()
                .code(dto.code())
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .balance(dto.initialBalance())
                .currency(dto.currency() != null ? dto.currency() : "XOF")
                .build();
    }

    public static WalletResponseDto toDto(Wallet wallet) {
        return new WalletResponseDto(
                wallet.getId(),
                wallet.getCode(),
                wallet.getPhoneNumber(),
                wallet.getEmail(),
                wallet.getBalance(),
                wallet.getCurrency(),
                wallet.getCreatedAt()
        );
    }

    public static TransactionResponseDto toTransactionDto(Transaction t) {
        return new TransactionResponseDto(
                t.getId(),
                t.getType(),
                t.getAmount(),
                t.getFees(),
                t.getDescription(),
                t.getTargetPhone(),
                t.getCreatedAt()
        );
    }
}
