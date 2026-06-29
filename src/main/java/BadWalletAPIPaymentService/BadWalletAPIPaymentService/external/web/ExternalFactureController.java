package BadWalletAPIPaymentService.BadWalletAPIPaymentService.external.web;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.response.RestResponse;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service.FactureService;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.FactureResponseDto;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.mapper.FactureMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/external/factures")
@CrossOrigin(origins = "http://localhost:4200")

public class ExternalFactureController {

    private final FactureService factureService;

    public ExternalFactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping("/{walletCode}/current")
    public ResponseEntity<RestResponse<List<FactureResponseDto>>> getFacturesMoisCourant(
            @PathVariable String walletCode,
            @RequestParam(required = false) String unite) {
        List<FactureResponseDto> factures = factureService
                .getFacturesDuMoisCourant(walletCode, unite)
                .stream()
                .map(FactureMapper::toDto)
                .toList();
        String msg = unite != null
                ? "Factures du mois courant pour le service " + unite
                : "Factures du mois courant";
        return ResponseEntity.ok(RestResponse.success(msg, factures));
    }


    @GetMapping("/{walletCode}/periode")
    public ResponseEntity<RestResponse<List<FactureResponseDto>>> getFacturesSurPeriode(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<FactureResponseDto> factures = factureService
                .getFacturesSurPeriode(walletCode, debut, fin)
                .stream()
                .map(FactureMapper::toDto)
                .toList();
        return ResponseEntity.ok(RestResponse.success(
                "Factures du " + debut + " au " + fin, factures));
    }
}
