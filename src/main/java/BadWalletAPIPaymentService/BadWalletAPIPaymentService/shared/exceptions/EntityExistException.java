package BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.exceptions;

public class EntityExistException extends RuntimeException {
    public EntityExistException(String message) {
        super(message);
    }
}
