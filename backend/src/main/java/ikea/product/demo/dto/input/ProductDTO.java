package ikea.product.demo.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Input data for creating or updating a product")
public class ProductDTO {
    @NotBlank(message = "Product type is required")
    @Schema(description = "The type or category of the product", example = "Chair")
    private String productType;

    @NotBlank(message = "Colour is required")
    @Schema(description = "The colour of the product", example = "Blue")
    private String colour;

    @NotBlank(message = "Name is required")
    @Schema(description = "The name of the product", example = "POÄNG")
    private String name;
}