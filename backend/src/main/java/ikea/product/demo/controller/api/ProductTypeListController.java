package ikea.product.demo.controller.api;

import ikea.product.demo.dto.response.ProductTypeResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.service.ProductTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing product types.
 */
@RestController
@RequestMapping("/api/product-types")
@Tag(name = "Product Type Management", description = "Endpoints for managing product types")
@RequiredArgsConstructor
public class ProductTypeListController {
    private final ProductTypeService productTypeService;

    /**
     * Retrieves all available product types.
     *
     * @return A list of all product type details
     */
    @GetMapping
    @Operation(
            summary = "List all product types",
            description = "Retrieves a list of all available product types."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved product types",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
            )
    )
    public ResponseEntity<SuccessResponse<List<ProductTypeResponse>>> getAllProductTypes() {
        return productTypeService.getAllProductTypes();
    }
}