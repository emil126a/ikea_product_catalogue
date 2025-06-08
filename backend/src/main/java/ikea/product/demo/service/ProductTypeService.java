package ikea.product.demo.service;

import ikea.product.demo.dto.response.ProductTypeResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.entity.ProductType;
import ikea.product.demo.mapper.ProductTypeMapper;
import ikea.product.demo.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper productTypeMapper;

    public ResponseEntity<SuccessResponse<List<ProductTypeResponse>>> getAllProductTypes() {
        List<ProductType> productTypes = productTypeRepository.findAll();
        List<ProductTypeResponse> productTypeResponses = productTypes.stream()
                .map(productTypeMapper::toResponse)
                .collect(Collectors.toList());

        SuccessResponse<List<ProductTypeResponse>> successResponse =
                new SuccessResponse<>(true, productTypeResponses);
        return ResponseEntity.ok(successResponse);
    }
}