package ikea.product.demo.dto.output;

import ikea.product.demo.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * Constructs a ProductListResponseDTO from a Spring Data Page object.
 */
@Data
@AllArgsConstructor
@Schema(description = "Paginated response containing a list of products")
public class ProductListResponseDTO<T> {

    @Schema(description = "List of items in the current page")
    private List<Product> products;

    @Schema(description = "Pagination information")
    private PaginatedResponseDTO pagination;
}