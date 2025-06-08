package ikea.product.demo.mapper;

import ikea.product.demo.dto.response.ColourResponse;
import ikea.product.demo.entity.Colour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColourMapper {
    ColourResponse toResponse(Colour colour);
}
