package ikea.product.demo.service;

import ikea.product.demo.dto.response.ColourResponse;
import ikea.product.demo.dto.response.SuccessResponse;
import ikea.product.demo.entity.Colour;
import ikea.product.demo.mapper.ColourMapper;
import ikea.product.demo.repository.ColourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColourService {
    private final ColourRepository colourRepository;
    private final ColourMapper colourMapper;

    public ResponseEntity<SuccessResponse<List<ColourResponse>>> getAllColours() {
        List<Colour> colours = colourRepository.findAll();
        List<ColourResponse> colourResponses = colours.stream()
                .map(colourMapper::toResponse)
                .collect(Collectors.toList());

        SuccessResponse<List<ColourResponse>> successResponse = new SuccessResponse<>(true, colourResponses);
        return ResponseEntity.ok(successResponse);
    }
}