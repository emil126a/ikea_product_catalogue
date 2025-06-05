package ikea.product.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handleException(ProductNotFoundException exception) {
        ProductErrorResponse error = new ProductErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
