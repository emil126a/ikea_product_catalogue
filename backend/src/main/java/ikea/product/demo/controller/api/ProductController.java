package ikea.product.demo.controller.api;

import ikea.product.demo.dto.error.ValidationErrorResponseDTO;
import ikea.product.demo.dto.input.ProductDTO;
import ikea.product.demo.dto.output.*;
import ikea.product.demo.entity.Colour;
import ikea.product.demo.entity.Product;
import ikea.product.demo.entity.ProductType;
import ikea.product.demo.exception.ProductNotFoundException;
import ikea.product.demo.repository.ColourRepository;
import ikea.product.demo.repository.ProductRepository;
import ikea.product.demo.repository.ProductTypeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing products.
 * Provides endpoints for listing, retrieving, and creating products.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Product Management", description = "Endpoints for managing product inventory")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ColourRepository colourRepository;

    public ProductController(ProductRepository productRepository,
                             ProductTypeRepository productTypeRepository,
                             ColourRepository colourRepository
    ) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.colourRepository = colourRepository;
    }

    /**
     * Retrieves a paginated list of products sorted by creation date.
     *
     * @param pageable PaginationDTO and sorting parameters (default: 10 items, sorted by createdAt DESC)
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
                    schema = @Schema(implementation = PaginatedApiResponseDTO.class)
            )
    )
    public ResponseEntity<PaginatedApiResponseDTO<List<ProductOutputDTO>>> listProducts(
            @Parameter(
                    description = "Pagination and sorting parameters",
                    example = "{\"page\":0,\"size\":10,\"sort\":\"createdAt,desc\"}",
                    schema = @Schema(implementation = Pageable.class)
            )
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            @PageableDefault(size = 10) final Pageable pageable
    ) {
        Page<Product> products = productRepository.findAll(pageable);

        PaginationDTO pagination = new PaginationDTO(
                products.getPageable(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.getNumberOfElements(),
                products.isFirst(),
                products.isLast(),
                products.isEmpty()
        );

        List<ProductOutputDTO> productOutputDTOList = new ArrayList<>();

        for (Product product : products.getContent()) {
            productOutputDTOList.add(buildProductOutputDTO(product));
        }

        PaginatedApiResponseDTO<List<ProductOutputDTO>> paginatedApiResponseDTO = new PaginatedApiResponseDTO<>(true, productOutputDTOList, pagination);

        return ResponseEntity.ok(paginatedApiResponseDTO);
    }

    @PostMapping("/products")
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved products",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "ValidationError",
                                    summary = "Validation failure example",
                                    value = """
                                            {
                                                "success": false,
                                                "error": {
                                                    "message": "Validation failed",
                                                    "details": {
                                                        "colourIds": "One or more colour IDs are invalid or do not exist",
                                                        "name": "Name is required",
                                                        "productTypeId": "Invalid product type ID: must exist in the database"
                                                    },
                                                    "code": 400
                                                }
                                            }
                                            """
                            )))
    })
    @Transactional
    public ResponseEntity<ApiResponseDTO<ProductOutputDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductType productType = productTypeRepository.findOneById(productDTO.getProductTypeId());
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCreatedAt(LocalDateTime.now());
        product.setProductType(productType);

        for (int id : productDTO.getColourIds()) {
            Colour colour = colourRepository.findOneById(id);
            product.addColour(colour);
        }
        productRepository.save(product);

        ProductOutputDTO outputProductDTO = buildProductOutputDTO(product);
        ApiResponseDTO<ProductOutputDTO> apiResponseDTO = new ApiResponseDTO<>(true, outputProductDTO);
        return ResponseEntity.ok(apiResponseDTO);
    }

    private ProductOutputDTO buildProductOutputDTO(Product product) {
        ProductOutputDTO outputProductDTO = new ProductOutputDTO();
        outputProductDTO.setId(product.getId());
        outputProductDTO.setName(product.getName());
        outputProductDTO.setCreatedAt(product.getCreatedAt().toString());
        outputProductDTO.setProductType(
                new ProductTypeDTO(
                        product.getProductType().getId(),
                        product.getProductType().getName()
                )
        );
        outputProductDTO.setColours(product.getColours().stream()
                .map(colour -> new ColourDTO(
                        colour.getId(),
                        colour.getName())
                )
                .collect(Collectors.toList()));

        return outputProductDTO;
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
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"success\": false,\n" +
                                            "  \"error\": {\n" +
                                            "    \"message\": \"Product not found id: 123\",\n" +
                                            "    \"code\": 404\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
    })
    public ResponseEntity<ApiResponseDTO<ProductOutputDTO>> getProductById(
            @Parameter(
                    name = "id",
                    description = "ID of the product to retrieve",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found id: " + id)
                );

        ProductOutputDTO productOutputDTO = buildProductOutputDTO(product);
        ApiResponseDTO<ProductOutputDTO> apiResponse = new ApiResponseDTO<>(true, productOutputDTO);

        return ResponseEntity.ok(apiResponse);
    }
}
