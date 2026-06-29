package BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public RestResponse(boolean success, String message, T data) {
        this(success, message, data, LocalDateTime.now());
    }

    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(true, "Opération réussie", data);
    }

    public static <T> RestResponse<T> success(String message, T data) {
        return new RestResponse<>(true, message, data);
    }

    public static <T> RestResponse<T> error(String message) {
        return new RestResponse<>(false, message, null);
    }

    public static <T> RestResponse<T> error(String message, T data) {
        return new RestResponse<>(false, message, data);
    }
}
