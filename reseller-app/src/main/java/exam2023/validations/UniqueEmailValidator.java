package exam2023.validations;

import exam2023.services.UserService;
import exam2023.validations.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
