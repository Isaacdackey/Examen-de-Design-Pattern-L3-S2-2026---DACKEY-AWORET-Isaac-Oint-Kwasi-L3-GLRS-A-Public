package BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.repository;

import BadWalletAPIPaymentService.BadWalletAPIPaymentService.wallet.data.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByPhoneNumber(String phoneNumber);
    Optional<Wallet> findByCode(String code);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Page<Wallet> findAll(Pageable pageable);
}
