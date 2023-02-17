package shop.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import shop.services.UserService;
import shop.validations.annotations.UniqueUsername;

public record UniqueUsernameValidator(UserService userService)
        implements ConstraintValidator<UniqueUsername, String> {
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return this.userService.getUserByUsername(username) == null;
    }
}
