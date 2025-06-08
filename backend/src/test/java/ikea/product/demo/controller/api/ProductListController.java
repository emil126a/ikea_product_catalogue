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
                1,    // currentPage (1-based)
                10L,  // totalElements
                2,    // totalPages
                10    // limit
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
        assertEquals("Billy Bookcase", response.getBody().getData().getFirst().getName());

        // Verify pagination (1-based)
        PaginationResponse pagination = response.getBody().getPagination();
        assertNotNull(pagination);
        assertEquals(1, pagination.getCurrentPage());
        assertEquals(10L, pagination.getTotalElements());
        assertEquals(2, pagination.getTotalPages());
        assertEquals(10, pagination.getLimit());
    }

    @Test
    void listProducts_WithCustomPageable_ReturnsPaginatedResponse() {
        // Arrange
        Pageable customPageable = PageRequest.of(1, 5, Sort.by(Sort.Direction.ASC, "name"));
        mockPagination = new PaginationResponse(
                2,    // currentPage (1-based, second page)
                10L,  // totalElements
                2,    // totalPages
                5     // limit
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
        assertEquals(2, pagination.getCurrentPage());
        assertEquals(10L, pagination.getTotalElements());
        assertEquals(2, pagination.getTotalPages());
        assertEquals(5, pagination.getLimit());
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

    @Test
    void listProducts_FirstPage_ReturnsPage1() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(productService.listProducts(any(Pageable.class)))
                .thenReturn(ResponseEntity.ok(mockPaginatedResponse));

        // Act
        ResponseEntity<PaginatedListResponse<List<ProductResponse>>> response =
                productListController.listProducts(pageable);

        // Assert
        assertEquals(1, response.getBody().getPagination().getCurrentPage());
    }
}