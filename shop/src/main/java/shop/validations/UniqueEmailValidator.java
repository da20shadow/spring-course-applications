package shop.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import shop.services.UserService;
import shop.validations.annotations.UniqueEmail;

public record UniqueEmailValidator(UserService userService)
        implements ConstraintValidator<UniqueEmail,String> {
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return this.userService.getUserByEmail(email) == null;
    }
}
