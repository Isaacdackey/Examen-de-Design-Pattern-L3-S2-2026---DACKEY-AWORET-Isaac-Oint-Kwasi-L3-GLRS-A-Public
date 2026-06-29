package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.exceptions.EntityNotFoundException;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.response.PageResponse;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.response.RestResponse;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Transaction;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service.WalletService;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.*;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.mapper.WalletMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }




    @PostMapping
    public ResponseEntity<RestResponse<WalletResponseDto>> createWallet(
            @Valid @RequestBody WalletCreateRequestDto dto) {
        WalletResponseDto created = walletService.createWallet(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.success("Portefeuille créé avec succès", created));
    }

    @GetMapping
    public ResponseEntity<RestResponse<PageResponse<WalletResponseDto>>> getAllWallets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var pageResult = walletService.getAllWallets(PageRequest.of(page, size));
        return ResponseEntity.ok(RestResponse.success(
                "Liste des portefeuilles récupérée",
                PageResponse.fromPage(pageResult)));
    }


    @GetMapping("/{phoneNumber}")
    public ResponseEntity<RestResponse<WalletResponseDto>> getWalletByPhone(
            @PathVariable String phoneNumber) {
        WalletResponseDto wallet = walletService.getWalletByPhone(phoneNumber);
        return ResponseEntity.ok(RestResponse.success("Portefeuille récupéré", wallet));
    }

    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<RestResponse<BigDecimal>> getBalance(@PathVariable String phoneNumber) {
        BigDecimal balance = walletService.getBalance(phoneNumber);
        return ResponseEntity.ok(RestResponse.success("Solde récupéré", balance));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<RestResponse<TransactionResponseDto>> deposit(
            @PathVariable Long id,
            @Valid @RequestBody DepositRequestDto dto) {
        TransactionResponseDto tx = walletService.deposit(id, dto);
        return ResponseEntity.ok(RestResponse.success("Dépôt effectué avec succès", tx));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<RestResponse<TransactionResponseDto>> withdraw(
            @Valid @RequestBody WithdrawRequestDto dto) {
        TransactionResponseDto tx = walletService.withdraw(dto);
        return ResponseEntity.ok(RestResponse.success("Retrait effectué avec succès", tx));
    }

    @PostMapping("/transfer")
    public ResponseEntity<RestResponse<TransactionResponseDto>> transfer(
            @Valid @RequestBody TransferRequestDto dto) {
        TransactionResponseDto tx = walletService.transfer(dto);
        return ResponseEntity.ok(RestResponse.success("Transfert effectué avec succès", tx));
    }

    @PostMapping("/pay")
    public ResponseEntity<RestResponse<TransactionResponseDto>> pay(
            @Valid @RequestBody PayRequestDto dto) {
        TransactionResponseDto tx = walletService.pay(dto);
        return ResponseEntity.ok(RestResponse.success("Paiement effectué avec succès", tx));
    }

    @GetMapping("/{phoneNumber}/transactions")
    public ResponseEntity<RestResponse<List<TransactionResponseDto>>> getTransactionHistory(
            @PathVariable String phoneNumber) {
        List<TransactionResponseDto> history = walletService.getTransactionHistory(phoneNumber);
        return ResponseEntity.ok(RestResponse.success("Historique des transactions récupéré", history));
    }


}
