package ikea.product.demo.controller.api;

import ikea.product.demo.dto.response.PaginatedResponse;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for listing products.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "Endpoints for listing products")
@RequiredArgsConstructor
public class ProductListController {
    private final ProductService productService;

    /**
     * Retrieves a paginated list of products sorted by creation date.
     *
     * @param pageable Pagination and sorting parameters (default: 10 items, sorted by createdAt DESC)
     * @return A paginated response containing product details
     */
    @GetMapping
    @Operation(
            summary = "List all products",
            description = "Retrieves a paginated list of all products."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved products",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PaginatedResponse.class)
            )
    )
    public ResponseEntity<PaginatedResponse<List<ProductResponse>>> listProducts(
            @Parameter(
                    description = "Pagination and sorting parameters",
                    example = "{\"page\":0,\"size\":10,\"sort\":\"createdAt,desc\"}",
                    schema = @Schema(implementation = Pageable.class)
            )
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            @PageableDefault(size = 10) final Pageable pageable
    ) {
        return productService.listProducts(pageable);
    }
}