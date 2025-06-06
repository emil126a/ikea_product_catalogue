package ikea.product.demo.controller.api;

import ikea.product.demo.dto.input.ProductDTO;
import ikea.product.demo.dto.output.PaginatedResponseDTO;
import ikea.product.demo.dto.output.ProductListResponseDTO;
import ikea.product.demo.entity.Product;
import ikea.product.demo.exception.ProductNotFoundException;
import ikea.product.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;


/**
 * REST controller for managing products.
 * Provides endpoints for listing, retrieving, and creating products.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Product Management", description = "Endpoints for managing product inventory")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves a paginated list of products sorted by creation date.
     *
     * @param pageable PaginatedResponseDTO and sorting parameters (default: 10 items, sorted by createdAt DESC)
     * @return A paginated response containing product details
     */
    @GetMapping("/products")
    @Operation(
            summary = "List all products",
            description = "Retrieves a paginated list of all products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                    content = @Content(schema = @Schema(implementation = ProductListResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductListResponseDTO<Product>> listProducts(
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            @PageableDefault(size = 10) final Pageable pageable
    ) {
        Page<Product> products = productRepository.findAll(pageable);

        PaginatedResponseDTO pagination = new PaginatedResponseDTO(
                products.getPageable(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.getNumberOfElements(),
                products.isFirst(),
                products.isLast(),
                products.isEmpty()
        );

        ProductListResponseDTO<Product> response = new ProductListResponseDTO<>(true, products.getContent(), pagination);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products")
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Product createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        return product;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found id: " + id)
                );
    }
}
