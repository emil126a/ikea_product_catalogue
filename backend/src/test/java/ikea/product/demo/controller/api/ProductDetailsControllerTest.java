package ikea.product.demo.controller.api;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductDetailsControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductDetailsController productDetailsController;

    private ProductResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new ProductResponse();
        mockResponse.setId(1);
        mockResponse.setName("Billy Bookcase");
    }

    @Test
    void getProductById_WithValidId_ReturnsSuccessResponse() {
        // Arrange
        SuccessResponse<ProductResponse> successResponse = new SuccessResponse<>();
        successResponse.setData(mockResponse);

        when(productService.getProductById(anyInt()))
                .thenReturn(ResponseEntity.ok(successResponse));

        // Act
        ResponseEntity<SuccessResponse<ProductResponse>> response =
                productDetailsController.getProductById(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Billy Bookcase", response.getBody().getData().getName());
    }

    @Test
    void getProductById_WithNonExistentId_ReturnsNotFound() {
        // Arrange
        when(productService.getProductById(anyInt()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        // Act
        ResponseEntity<SuccessResponse<ProductResponse>> response =
                productDetailsController.getProductById(999);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getProductById_ServiceReturnsError_PropagatesErrorResponse() {
        // Arrange
        when(productService.getProductById(anyInt()))
                .thenReturn(ResponseEntity.internalServerError().build());

        // Act
        ResponseEntity<SuccessResponse<ProductResponse>> response =
                productDetailsController.getProductById(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}