package ikea.product.demo.service;

import ikea.product.demo.dto.request.ProductRequest;
import ikea.product.demo.dto.response.*;
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
import ikea.product.demo.mapper.ColourMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ColourRepository colourRepository;
    private final ProductMapper productMapper;
    private final ColourMapper colourMapper;

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
                products.getNumber() + 1,
                products.getTotalElements(),
                products.getTotalPages(),
                products.getNumberOfElements()
        );

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : products.getContent()) {
            productResponseList.add(productMapper.toResponse(product));
        }

        PaginatedListResponse<List<ProductResponse>> paginatedListResponse = new PaginatedListResponse<>(true, productResponseList, pagination);

        return ResponseEntity.ok(paginatedListResponse);
    }

    public ResponseEntity<SuccessResponse<List<ColourResponse>>> getAllColours() {
        List<Colour> colours = colourRepository.findAll();
        List<ColourResponse> colourResponses = colours.stream()
                .map(colourMapper::toResponse)
                .collect(Collectors.toList());

        SuccessResponse<List<ColourResponse>> successResponse = new SuccessResponse<>(true, colourResponses);
        return ResponseEntity.ok(successResponse);
    }
}