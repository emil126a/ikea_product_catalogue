package ikea.product.demo.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaginatedApiResponseDTO<T> {
    @Schema(description = "Indicates if the request was successful")
    private boolean success;

    @Schema(description = "List of products and pagination metadata")
    private T data;

    @Schema(description = "Pagination details")
    private PaginationDTO pagination;
}