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

    Boolean success;

    @Schema(description = "Details of the error, including a message and field-specific errors")
    ErrorDetail error;

    @Value
    @Builder
    @Schema(description = "Error details with a message and field-specific validation errors")
    public static class ErrorDetail {
        @Schema(description = "General error message", example = "Validation failed")
        String message;

        @Schema(
                description = "Map of field names to error messages for validation failures",
                example = "{\"name\": \"Name is required\", \"productTypeId\": \"Invalid product type ID: must exist in the database\", \"colourIds\": \"One or more colour IDs are invalid or do not exist\"}"
        )
        Map<String, String> details;

        Integer code;
    }
}