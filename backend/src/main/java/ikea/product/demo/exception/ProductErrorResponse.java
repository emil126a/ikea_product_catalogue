package ikea.product.demo.exception;

import lombok.Data;

@Data
public class ProductErrorResponse {
    private boolean status;
    private String message;
}