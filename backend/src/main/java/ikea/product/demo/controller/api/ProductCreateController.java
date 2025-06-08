package ikea.product.demo.controller.api;

import ikea.product.demo.dto.request.ProductRequest;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.dto.response.ValidationErrorResponse;
import ikea.product.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for creating products.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "Endpoints for creating products")
@RequiredArgsConstructor
public class ProductCreateController {
    private final ProductService productService;

    /**
     * Creates a new product with the provided details.
     *
     * @param productRequest The product details to create
     * @return A response containing the created product details
     */
    @PostMapping
    @Operation(summary = "Create a new product",
            description = "Creates a new product with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created product",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class),
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
    public ResponseEntity<SuccessResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductRequest productRequest
    ) {
        return productService.createProduct(productRequest);
    }
}