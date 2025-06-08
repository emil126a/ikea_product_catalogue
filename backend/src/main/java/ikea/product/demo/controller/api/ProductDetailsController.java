package ikea.product.demo.controller.api;

import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for retrieving product details.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "Endpoints for retrieving product details")
@RequiredArgsConstructor
public class ProductDetailsController {
    private final ProductService productService;

    /**
     * Returns a product by its ID.
     *
     * @param id the ID of the product
     * @return a ResponseEntity with SuccessResponse containing the product if found,
     * or 404 error if not found
     */
    @GetMapping("/{id}")
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
                            schema = @Schema(implementation = SuccessResponse.class)
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
    public ResponseEntity<SuccessResponse<ProductResponse>> getProductById(
            @Parameter(
                    name = "id",
                    description = "ID of the product to retrieve",
                    example = "1",
                    required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable Integer id
    ) {
        return productService.getProductById(id);
    }
}