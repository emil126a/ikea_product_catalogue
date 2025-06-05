package ikea.product.demo.dto.input;
import ikea.product.demo.entity.Colour;
import ikea.product.demo.entity.Type;
import jakarta.validation.constraints.NotBlank;
public class ProductDTO {
    @NotBlank
    private Type productType;

    @NotBlank
    private Colour colour;

    @NotBlank
    private String name;
}

