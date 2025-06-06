package ikea.product.demo.exception;

import lombok.Data;

@Data
public class ProductErrorResponse {
    private int status;
    private String message;
}