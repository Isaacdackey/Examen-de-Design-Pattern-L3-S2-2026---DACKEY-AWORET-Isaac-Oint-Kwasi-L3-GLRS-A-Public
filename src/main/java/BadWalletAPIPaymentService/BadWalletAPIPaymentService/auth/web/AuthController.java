package BadWalletAPIPaymentService.BadWalletAPIPaymentService.auth.web;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.response.RestResponse;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository.WalletRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final WalletRepository walletRepository;

    public AuthController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse<Map<String, Object>>> login(@RequestBody Map<String, String> credentials) {
        String phoneNumber = credentials.get("phoneNumber");
        String password = credentials.get("password");

        Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber).orElse(null);

        if (wallet == null) {
            return ResponseEntity.status(401).body(
                    RestResponse.error("Numéro de téléphone ou mot de passe incorrect")
            );
        }

        Map<String, Object> data = new HashMap<>();
        data.put("token", "fake-jwt-token-" + System.currentTimeMillis());

        Map<String, Object> user = new HashMap<>();
        user.put("id", wallet.getId());
        user.put("phoneNumber", wallet.getPhoneNumber());
        user.put("email", wallet.getEmail());
        user.put("role", "CLIENT");
        user.put("username", wallet.getEmail());

        data.put("user", user);

        return ResponseEntity.ok(RestResponse.success("Connexion réussie", data));
    }

    @PostMapping("/register")
    public ResponseEntity<RestResponse<Map<String, Object>>> register(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String email = request.get("email");
        String password = request.get("password");
        String role = request.getOrDefault("role", "CLIENT");

        if (walletRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            return ResponseEntity.status(409).body(
                    RestResponse.error("Un compte existe déjà avec ce numéro de téléphone")
            );
        }


        Wallet wallet = new Wallet();
        wallet.setPhoneNumber(phoneNumber);
        wallet.setEmail(email);
        wallet.setCode("WLT-" + System.currentTimeMillis());
        wallet.setBalance(java.math.BigDecimal.ZERO);
        wallet.setCurrency("XOF");

        walletRepository.save(wallet);

        Map<String, Object> data = new HashMap<>();
        data.put("token", "fake-jwt-token-" + System.currentTimeMillis());

        Map<String, Object> user = new HashMap<>();
        user.put("id", wallet.getId());
        user.put("phoneNumber", wallet.getPhoneNumber());
        user.put("email", wallet.getEmail());
        user.put("role", role);
        user.put("username", wallet.getEmail());

        data.put("user", user);

        return ResponseEntity.ok(RestResponse.success("Inscription réussie", data));
    }
}