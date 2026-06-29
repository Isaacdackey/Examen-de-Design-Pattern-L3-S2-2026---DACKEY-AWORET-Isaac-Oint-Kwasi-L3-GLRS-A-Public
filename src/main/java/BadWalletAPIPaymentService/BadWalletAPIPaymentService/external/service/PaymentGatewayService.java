package BadWalletAPIPaymentService.BadWalletAPIPaymentService.external.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;


@Service
public class PaymentGatewayService {

    private final WebClient webClient;

    public PaymentGatewayService(@Value("${payment.service.url:http://localhost:8081}") String paymentServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(paymentServiceUrl)
                .build();
    }


    public void processStripePayment(BigDecimal amount) {
        try {
            webClient.post()
                    .uri("/api/payments/stripe")
                    .bodyValue(Map.of("amount", amount, "currency", "XOF"))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            System.out.println("[PaymentGateway] Stripe simulé (service externe indisponible): " + amount + " XOF");
        }
    }


    public void processPayment(String walletCode, String serviceName, BigDecimal amount) {
        try {
            webClient.post()
                    .uri("/api/payments/facture")
                    .bodyValue(Map.of(
                            "walletCode", walletCode,
                            "serviceName", serviceName,
                            "amount", amount
                    ))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            System.out.println("[PaymentGateway] Paiement simulé pour " + serviceName + " - " + amount + " XOF");
        }
    }
}
