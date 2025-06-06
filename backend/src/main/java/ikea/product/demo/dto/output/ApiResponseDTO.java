package ikea.product.demo.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponseDTO<T> {
    private boolean success;
    private T data;
}
