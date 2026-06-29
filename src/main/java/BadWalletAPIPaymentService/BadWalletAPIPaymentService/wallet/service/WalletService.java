package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

    void seedWallets(int numWallets, int eventsPerWallet);
    WalletResponseDto createWallet(WalletCreateRequestDto dto);

    Page<WalletResponseDto> getAllWallets(Pageable pageable);

    WalletResponseDto getWalletByPhone(String phoneNumber);

    BigDecimal getBalance(String phoneNumber);

    TransactionResponseDto deposit(Long walletId, DepositRequestDto dto);

    TransactionResponseDto withdraw(WithdrawRequestDto dto);

    TransactionResponseDto transfer(TransferRequestDto dto);

    TransactionResponseDto pay(PayRequestDto dto);

    List<TransactionResponseDto> payFactures(PayFacturesRequestDto dto);

    List<TransactionResponseDto> getTransactionHistory(String phoneNumber);
}
