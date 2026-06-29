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

        String[][] walletData = {
                {"+221770000001", "client1@gmail.com", "WLT-0000001", "150000"},
                {"+221770000002", "client2@gmail.com", "WLT-0000002", "80000"},
                {"+221770000003", "client3@gmail.com", "WLT-0000003", "200000"},
                {"+221770000004", "client4@gmail.com", "WLT-0000004", "50000"},
                {"+221770000005", "client5@gmail.com", "WLT-0000005", "320000"},
        };

        for (int i = 0; i < walletData.length; i++) {
            String[] data = walletData[i];

            Wallet wallet = walletFactory.createSeedWallet(
                    data[0], data[1], data[2], data[3]
            );
            walletRepository.save(wallet);

            BigDecimal initialBalance = new BigDecimal(data[3]);
            var transaction = transactionFactory.createDepositTransaction(
                    wallet, initialBalance, "Dépôt initial"
            );
            transactionService.saveDepot(wallet, initialBalance, "Dépôt initial");

            factureService.seedFactures(wallet, i + 1);
        }
    }
}