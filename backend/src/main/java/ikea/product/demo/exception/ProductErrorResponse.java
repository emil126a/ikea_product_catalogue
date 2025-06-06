package ikea.product.demo.exception;

import lombok.Data;

@Data
public class ProductErrorResponse {
    private boolean success;
    private ErrorDetails error;

    @Data
    public static class ErrorDetails {
        private String message;
        private int code;

        public ErrorDetails(String message, int code) {
            this.message = message;
            this.code = code;
        }
    }

    public ProductErrorResponse(String message, int code) {
        this.success = false;
        this.error = new ErrorDetails(message, code);
    }
}