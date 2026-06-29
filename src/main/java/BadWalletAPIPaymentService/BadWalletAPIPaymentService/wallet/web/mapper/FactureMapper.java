package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.mapper;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Facture;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.web.dto.FactureResponseDto;

public final class FactureMapper {

    private FactureMapper() {}

    public static FactureResponseDto toDto(Facture facture) {
        return new FactureResponseDto(
                facture.getId(),
                facture.getReference(),
                facture.getWalletCode(),
                facture.getServiceName(),
                facture.getMontant(),
                facture.isPayee(),
                facture.getDateFacture(),
                facture.getDatePaiement()
        );
    }
}
