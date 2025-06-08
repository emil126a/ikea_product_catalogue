package ikea.product.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PaginatedListResponse<T> {
    @Schema(description = "Indicates if the request was successful")
    private boolean success;

    @Schema(description = "List of products and pagination metadata")
    private T data;

    @Schema(description = "Pagination details")
    private PaginationResponse pagination;
}