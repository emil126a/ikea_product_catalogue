package ikea.product.demo.validation;


import ikea.product.demo.repository.ColourRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ColourIdsValidator implements ConstraintValidator<ValidColourIds, List<Integer>> {
    private final ColourRepository colourRepository;

    @Autowired
    public ColourIdsValidator(ColourRepository productTypeRepository) {
        this.colourRepository = productTypeRepository;
    }

    @Override
    public boolean isValid(List<Integer> colourIds, ConstraintValidatorContext context) {
        if (colourIds == null || colourIds.isEmpty()) {
            return true;
        }

        for (Integer colourId : colourIds) {
            if (colourId == null || !colourRepository.existsById(colourId)) {
                return false;
            }
        }

        return true;
    }
}
