package BadWalletAPIPaymentService.BadWalletAPIPaymentService.mock;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository.WalletRepository;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory.FactureFactory;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory.TransactionFactory;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.factory.WalletFactory;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service.FactureService;
import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class DataSeeder implements CommandLineRunner {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;
    private final FactureService factureService;
    private final WalletFactory walletFactory;
    private final TransactionFactory transactionFactory;
    private final FactureFactory factureFactory;

    public DataSeeder(WalletRepository walletRepository,
                      TransactionService transactionService,
                      FactureService factureService,
                      WalletFactory walletFactory,
                      TransactionFactory transactionFactory,
                      FactureFactory factureFactory) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
        this.factureService = factureService;
        this.walletFactory = walletFactory;
        this.transactionFactory = transactionFactory;
        this.factureFactory = factureFactory;
    }

    @Override
    public void run(String... args) {
        if (walletRepository.count() > 0) return;


        Object[][] walletData = {
                {"+221770000001", "client1@gmail.com", "WLT-0000001", "150000", "CLIENT"},
                {"+221770000002", "agent1@gmail.com", "WLT-0000002", "80000", "AGENT"},
                {"+221770000003", "agent2@gmail.com", "WLT-0000003", "200000", "AGENT"},
                {"+221770000004", "client4@gmail.com", "WLT-0000004", "50000", "CLIENT"},
                {"+221770000005", "client5@gmail.com", "WLT-0000005", "320000", "CLIENT"},
        };

        for (int i = 0; i < walletData.length; i++) {
            Object[] data = walletData[i];

            String phone = (String) data[0];
            String email = (String) data[1];
            String code = (String) data[2];
            String balanceStr = (String) data[3];
            String role = (String) data[4];

            Wallet wallet = Wallet.builder()
                    .phoneNumber(phone)
                    .email(email)
                    .code(code)
                    .balance(new BigDecimal(balanceStr))
                    .currency("XOF")
                    .role(role)
                    .build();

            walletRepository.save(wallet);


            BigDecimal initialBalance = new BigDecimal(balanceStr);
            transactionService.saveDepot(wallet, initialBalance, "Dépôt initial");


            factureService.seedFactures(wallet, i + 1);

            System.out.println("Wallet créé: " + phone + " - Rôle: " + role);
        }
    }
}