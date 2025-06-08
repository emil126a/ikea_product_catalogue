package ikea.product.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuccessResponse<T> {
    @Schema(description = "Indicates if the request was successful")
    private boolean success;

    @Schema(description = "The requested resource")
    private T data;
}