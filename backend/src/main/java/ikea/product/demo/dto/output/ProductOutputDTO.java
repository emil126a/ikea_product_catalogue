package ikea.product.demo.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutputDTO {
    private Integer id;
    private String name;
    private List<ColourDTO> colours;
    private ProductTypeDTO productType;
    private String createdAt;
}