package ikea.product.demo.controller.api;

import ikea.product.demo.dto.response.PaginatedListResponse;
import ikea.product.demo.dto.response.PaginationResponse;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductListControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductListController productListController;

    private ProductResponse mockProduct;
    private PaginatedListResponse<List<ProductResponse>> mockPaginatedResponse;
    private PaginationResponse mockPagination;

    @BeforeEach
    void setUp() {
        mockProduct = new ProductResponse();
        mockProduct.setId(1);
        mockProduct.setName("Billy Bookcase");

        mockPagination = new PaginationResponse(
                PageRequest.of(0, 10),
                1L,
                1,
                1,
                true,
                true,
                false
        );

        mockPaginatedResponse = new PaginatedListResponse<>(
                true,
                Collections.singletonList(mockProduct),
                mockPagination
        );
    }

    @Test
    void listProducts_WithDefaultPageable_ReturnsPaginatedResponse() {
        // Arrange
        Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        when(productService.listProducts(any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(mockPaginatedResponse));

        // Act
        ResponseEntity<PaginatedListResponse<List<ProductResponse>>> response =
                productListController.listProducts(defaultPageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());

        // Verify data
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Billy Bookcase", response.getBody().getData().get(0).getName());

        // Verify pagination
        PaginationResponse pagination = response.getBody().getPagination();
        assertNotNull(pagination);
        assertEquals(1L, pagination.getTotalElements());
        assertEquals(1, pagination.getTotalPages());
        assertEquals(1, pagination.getNumberOfElements());
    }

    @Test
    void listProducts_WithCustomPageable_ReturnsPaginatedResponse() {
        // Arrange
        Pageable customPageable = PageRequest.of(1, 5, Sort.by(Sort.Direction.ASC, "name"));
        mockPagination = new PaginationResponse(
                customPageable,
                2L,
                2,
                1,
                false,
                true,
                false
        );
        mockPaginatedResponse = new PaginatedListResponse<>(
                true,
                Collections.singletonList(mockProduct),
                mockPagination
        );

        when(productService.listProducts(any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(mockPaginatedResponse));

        // Act
        ResponseEntity<PaginatedListResponse<List<ProductResponse>>> response =
                productListController.listProducts(customPageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PaginationResponse pagination = response.getBody().getPagination();
        assertEquals(2L, pagination.getTotalElements());
        assertEquals(2, pagination.getTotalPages());
        assertEquals(1, pagination.getNumberOfElements());

    }

    @Test
    void listProducts_WhenNoProductsFound_ReturnsEmptyPaginatedResponse() {
        // Arrange
        Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        PaginationResponse emptyPagination = new PaginationResponse(
                defaultPageable,
                0L,
                0,
                0,
                true,
                true,
                true
        );
        PaginatedListResponse<List<ProductResponse>> emptyResponse = new PaginatedListResponse<>(
                true,
                Collections.emptyList(),
                emptyPagination
        );

        when(productService.listProducts(any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(emptyResponse));

        // Act
        ResponseEntity<PaginatedListResponse<List<ProductResponse>>> response =
                productListController.listProducts(defaultPageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertTrue(response.getBody().getData().isEmpty());

        PaginationResponse pagination = response.getBody().getPagination();
        assertEquals(0L, pagination.getTotalElements());
        assertEquals(0, pagination.getTotalPages());
        assertEquals(0, pagination.getNumberOfElements());
 
    }

    @Test
    void listProducts_WhenServiceError_ReturnsErrorResponse() {
        // Arrange
        Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        when(productService.listProducts(any(Pageable.class)))
                .thenReturn(ResponseEntity.internalServerError().build());

        // Act
        ResponseEntity<PaginatedListResponse<List<ProductResponse>>> response =
                productListController.listProducts(defaultPageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}