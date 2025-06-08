package ikea.product.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@Schema(description = "PaginationResponse information")
public class PaginationResponse {
    @Schema(description = "Current page number (0-based)", example = "0")
    private Integer currentPage;

    @Schema(description = "Total number of elements across all pages", example = "2")
    private Long totalElements;

    @Schema(description = "Total number of pages", example = "1")
    private Integer totalPages;

    @Schema(description = "Number of elements in the current page", example = "2")
    private Integer limit;

}
