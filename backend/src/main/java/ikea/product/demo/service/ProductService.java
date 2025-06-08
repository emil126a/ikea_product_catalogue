package ikea.product.demo.service;

import ikea.product.demo.dto.request.ProductRequest;
import ikea.product.demo.dto.response.PaginatedListResponse;
import ikea.product.demo.dto.response.PaginationResponse;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.entity.Colour;
import ikea.product.demo.entity.Product;
import ikea.product.demo.entity.ProductType;
import ikea.product.demo.exception.ProductNotFoundException;
import ikea.product.demo.mapper.ProductMapper;
import ikea.product.demo.repository.ColourRepository;
import ikea.product.demo.repository.ProductRepository;
import ikea.product.demo.repository.ProductTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ColourRepository colourRepository;
    private final ProductMapper productMapper;

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ResponseEntity<SuccessResponse<ProductResponse>> createProduct(ProductRequest request) {
        ProductType productType = productTypeRepository.findOneById(request.getProductTypeId());
        Product product = productMapper.toEntity(request);
        product.setProductType(productType);
        product.setCreatedAt(LocalDateTime.now());

        request.getColourIds().forEach(id -> {
            Colour colour = colourRepository.findOneById(id);
            product.addColour(colour);
        });

        productRepository.save(product);

        ProductResponse productResponse = productMapper.toResponse(product);
        SuccessResponse<ProductResponse> successResponse = new SuccessResponse<>(true, productResponse);

        return ResponseEntity.ok(successResponse);
    }

    public ResponseEntity<SuccessResponse<ProductResponse>> getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        ProductResponse productResponse = productMapper.toResponse(product);
        SuccessResponse<ProductResponse> successResponse = new SuccessResponse<>(true, productResponse);

        return ResponseEntity.ok(successResponse);
    }

    public ResponseEntity<PaginatedListResponse<List<ProductResponse>>> listProducts(Pageable pageable) {
        Page<Product> products = this.findAllProducts(pageable);

        PaginationResponse pagination = new PaginationResponse(
                products.getPageable(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.getNumberOfElements(),
                products.isFirst(),
                products.isLast(),
                products.isEmpty()
        );

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : products.getContent()) {
            productResponseList.add(productMapper.toResponse(product));
        }

        PaginatedListResponse<List<ProductResponse>> paginatedListResponse = new PaginatedListResponse<>(true, productResponseList, pagination);

        return ResponseEntity.ok(paginatedListResponse);
    }
}