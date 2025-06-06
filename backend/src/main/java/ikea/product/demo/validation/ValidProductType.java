package ikea.product.demo.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation to ensure a product type exists in the database.
 */
@Constraint(validatedBy = ProductTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductType {
    String message() default "Invalid product type ID: must exist in the database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}