package ikea.product.demo.controller.api;

import ikea.product.demo.dto.request.ProductRequest;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCreateControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductCreateController productCreateController;

    private ProductRequest validRequest;
    private ProductRequest invalidRequest;
    private ProductResponse mockResponse;

    @BeforeEach
    void setUp() {
        validRequest = new ProductRequest();
        validRequest.setName("Billy Bookcase");

        validRequest.setProductTypeId(1);
        validRequest.setColourIds(List.of(1, 2));

        invalidRequest = new ProductRequest();
        invalidRequest.setName(" ");
        invalidRequest.setProductTypeId(null);

        mockResponse = new ProductResponse();
        mockResponse.setId(1);
        mockResponse.setName("Billy Bookcase");
    }

    @Test
    void createProduct_WithValidRequest_ReturnsSuccessResponse() {
        // Arrange
        SuccessResponse<ProductResponse> successResponse = new SuccessResponse<>();
        successResponse.setData(mockResponse);

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(ResponseEntity.ok(successResponse));

        // Act
        ResponseEntity<SuccessResponse<ProductResponse>> response =
                productCreateController.createProduct(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Billy Bookcase", response.getBody().getData().getName());
    }

    @Test
    void createProduct_WithInvalidRequest_ThrowsMethodArgumentNotValidException() {
        // Arrange
        BindingResult bindingResult = new BeanPropertyBindingResult(invalidRequest, "productRequest");
        bindingResult.addError(new FieldError("productRequest", "name", "Name is required"));
        bindingResult.addError(new FieldError("productRequest", "productTypeId", "Product type ID is required"));

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(null, bindingResult);

        // Act & Assert
        assertThrows(MethodArgumentNotValidException.class, () -> {
            throw exception;
        });

        assertEquals(2, exception.getBindingResult().getErrorCount());
        assertTrue(exception.getBindingResult().hasFieldErrors("name"));
        assertTrue(exception.getBindingResult().hasFieldErrors("productTypeId"));
    }

    @Test
    void createProduct_ServiceReturnsError_PropagatesErrorResponse() {
        // Arrange
        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(ResponseEntity.internalServerError().build());

        // Act
        ResponseEntity<SuccessResponse<ProductResponse>> response =
                productCreateController.createProduct(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}