package ikea.product.demo.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * Represents the outcome of a failed API request, including validation or processing errors.
 */
@Value
@Builder
@Schema(description = "Error response containing validation errors")
public class ValidationErrorResponseDTO {
    @Schema(description = "Indicates whether the operation was successful", example = "false")
    boolean success;

    @Schema(
            description = "Map of field names to error messages for validation failures",
            example = "{\"productType\": \"Product type is required\", \"colour\": \"Colour is required\"}"
    )
    Map<String, String> errors;
}