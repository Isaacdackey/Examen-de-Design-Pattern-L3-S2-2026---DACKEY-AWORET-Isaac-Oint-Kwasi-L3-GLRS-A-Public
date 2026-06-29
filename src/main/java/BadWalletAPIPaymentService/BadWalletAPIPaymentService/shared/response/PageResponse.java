package BadWalletAPIPaymentService.BadWalletAPIPaymentService.shared.response;

import org.springframework.data.domain.Page;
import java.util.List;

public record PageResponse<T>(
        List<T> data,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean isFirst,
        boolean isLast
) {
    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }
}
