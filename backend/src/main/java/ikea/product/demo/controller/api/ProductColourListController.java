package ikea.product.demo.controller.api;

import ikea.product.demo.dto.response.ColourResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.service.ColourService;
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
 * REST controller for managing product colours.
 */
@RestController
@RequestMapping("/api/colours")
@Tag(name = "Colour Management", description = "Endpoints for managing product colours")
@RequiredArgsConstructor
public class ProductColourListController {
    private final ColourService colourService;

    /**
     * Retrieves all available product colours.
     *
     * @return A list of all colour details
     */
    @GetMapping
    @Operation(
            summary = "List all colours",
            description = "Retrieves a list of all available product colours."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved colours",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
            )
    )
    public ResponseEntity<SuccessResponse<List<ColourResponse>>> getAllColours() {
        return colourService.getAllColours();
    }
}