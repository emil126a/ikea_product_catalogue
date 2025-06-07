package ikea.product.demo.validation;

import ikea.product.demo.repository.ProductTypeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeValidator implements ConstraintValidator<ValidProductType, Integer> {

    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeValidator(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public boolean isValid(Integer productTypeId, ConstraintValidatorContext context) {
        if (productTypeId == null) {
            return false;
        }
        return productTypeRepository.existsById(productTypeId);
    }
}