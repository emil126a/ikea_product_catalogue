package ikea.product.demo.dto.request;

import ikea.product.demo.validation.ValidColourIds;
import ikea.product.demo.validation.ValidProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Schema(description = "Input data for creating or updating a product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "Product type is required")
    @Schema(description = "The type if of the product", example = "1")
    @ValidProductType
    private Integer productTypeId;

    @NotEmpty(message = "Colour is required")
    @Schema(description = "The colour ID of the product", example = "[1,2,3]")
    @ValidColourIds
    private List<Integer> colourIds;

    @NotBlank(message = "Name is required")
    @Schema(description = "The name of the product", example = "LANDSKRONA")
    private String name;
}