package ikea.product.demo.dto.input;

import jakarta.validation.constraints.NotBlank;

public class ProductDTO {
    @NotBlank(message = "Product type is required")
    private String productType;

    @NotBlank(message = "Colour is required")
    private String colour;

    @NotBlank(message = "Name is required")
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