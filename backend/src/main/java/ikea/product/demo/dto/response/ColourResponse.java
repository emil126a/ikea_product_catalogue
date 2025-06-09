package ikea.product.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColourResponse {
    private Integer id;
    private String name;
    private String hexcode;
}