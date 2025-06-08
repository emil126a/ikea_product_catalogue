package ikea.product.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ColourIdsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidColourIds {
    String message() default "One or more colour IDs are invalid or do not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
