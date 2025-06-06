package ikea.product.demo.dto.input;

import ikea.product.demo.entity.Colour;
import ikea.product.demo.entity.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {
    @NotBlank(message = "Product type is required")
    private String productType;

    @NotBlank
    private String colour;

    @NotBlank
    private String name;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

