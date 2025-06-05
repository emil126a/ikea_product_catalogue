package ikea.product.demo.dto.input;
import ikea.product.demo.entity.Colour;
import ikea.product.demo.entity.ProductType;
import jakarta.validation.constraints.NotBlank;
public class ProductDTO {


    @NotBlank
    private ProductType productType;

    @NotBlank
    private Colour colour;

    @NotBlank
    private String name;
}

