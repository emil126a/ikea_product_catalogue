package ikea.product.demo.mapper;

import ikea.product.demo.dto.response.ProductTypeResponse;
import ikea.product.demo.entity.ProductType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypeResponse toResponse(ProductType productType);
}