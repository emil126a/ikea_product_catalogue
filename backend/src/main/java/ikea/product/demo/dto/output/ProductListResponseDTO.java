package ikea.product.demo.dto.output;

import ikea.product.demo.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Constructs a ProductListResponseDTO from a Spring Data Page object.
 */
@Data
@Schema(description = "Paginated response containing a list of products")
public class ProductListResponseDTO<T> {
    @Schema(description = "List of items in the current page")
    private List<Product> content;

    @Schema(description = "Pagination information")
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

    /**
     * Constructs a ProductListResponseDTO from a Spring Data Page object.
     *
     * @param content         The list of items in the current page.
     * @param pageable        The pagination information.
     * @param totalElements   The total number of elements across all pages.
     * @param totalPages      The total number of pages.
     * @param numberOfElements The number of elements in the current page.
     * @param first           Whether this is the first page.
     * @param last            Whether this is the last page.
     * @param empty           Whether the page is empty.
     */
    public ProductListResponseDTO(List<Product> content,
                                  Pageable pageable,
                                  Long totalElements,
                                  Integer totalPages,
                                  Integer numberOfElements,
                                  Boolean first,
                                  Boolean last,
                                  Boolean empty
    ) {
        this.content = content;
        this.pageable = pageable;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.last = last;
        this.empty = empty;
    }
}