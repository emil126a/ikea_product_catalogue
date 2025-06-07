package ikea.product.demo.exception;

import ikea.product.demo.dto.error.ValidationErrorResponseDTO;
import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class ProductRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ProductErrorResponse> handleProductNotFound(ProductNotFoundException exception) {
        ProductErrorResponse error = new ProductErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // Handle duplicate fields
                ));

        ValidationErrorResponseDTO response = ValidationErrorResponseDTO.builder()
                .error(ValidationErrorResponseDTO.ErrorDetail.builder()
                        .message("Validation failed")
                        .details(details)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .build()
                )
                .success(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}