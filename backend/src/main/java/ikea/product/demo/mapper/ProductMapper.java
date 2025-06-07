package ikea.product.demo.mapper;

import ikea.product.demo.dto.request.ProductRequest;
import ikea.product.demo.dto.response.ProductResponse;
import ikea.product.demo.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toRequest(ProductRequest request);

    ProductResponse toResponse(Product product);
}