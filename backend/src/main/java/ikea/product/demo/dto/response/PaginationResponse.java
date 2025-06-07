package ikea.product.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@Schema(description = "PaginationResponse information")
public class PaginationResponse {
    @Schema(description = "PaginationResponse information")
    private Pageable pageable;

    @Schema(description = "Total number of elements across all pages", example = "2")
    private Long totalElements;

    @Schema(description = "Total number of pages", example = "1")
    private Integer totalPages;

    @Schema(description = "Number of elements in the current page", example = "2")
    private Integer numberOfElements;

    @Schema(description = "Whether this is the first page", example = "true")
    private Boolean first;

    @Schema(description = "Whether this is the last page", example = "true")
    private Boolean last;

    @Schema(description = "Whether the page is empty", example = "false")
    private Boolean empty;
}
