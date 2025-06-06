package ikea.product.demo.controller.api;

import ikea.product.demo.dto.input.ProductDTO;
import ikea.product.demo.dto.output.ApiResponseDTO;
import ikea.product.demo.dto.output.PaginatedResponseDTO;
import ikea.product.demo.dto.output.ProductListResponseDTO;
import ikea.product.demo.entity.Product;
import ikea.product.demo.exception.ProductNotFoundException;
import ikea.product.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved products",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductListResponseDTO.class)
            )
    )
    public ResponseEntity<ApiResponseDTO<ProductListResponseDTO<Product>>> listProducts(
            @Parameter(
                    description = "Pagination and sorting parameters",
                    example = "{\"page\":0,\"size\":10,\"sort\":\"createdAt,desc\"}",
                    schema = @Schema(implementation = Pageable.class)
            )
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

        ProductListResponseDTO<Product> response = new ProductListResponseDTO<>(products.getContent(), pagination);
        ApiResponseDTO<ProductListResponseDTO<Product>> apiResponse = new ApiResponseDTO<>(true, response);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Creates a new product with the given data.
     *
     * @param productDTO the product data from the request body
     * @return the created Product entity
     */
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

    /**
     * Returns a product by its ID.
     *
     * @param id the ID of the product
     * @return a ResponseEntity with ApiResponseDTO containing the product if found,
     * or 404 error if not found
     */
    @GetMapping("/products/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a specific product by its unique identifier"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found and returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"success\": false, \"message\": \"Product not found id: 123\"}"
                            )
                    )
            )
    })
    public ResponseEntity<ApiResponseDTO<Product>> getProductById(
            @Parameter(
                    name = "id",
                    description = "ID of the product to retrieve",
                    example = "123",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found id: " + id)
                );
        ApiResponseDTO<Product> apiResponse = new ApiResponseDTO<>(true, product);

        return ResponseEntity.ok(apiResponse);
    }
}
