package exam2023.validations;

import exam2023.services.UserService;
import exam2023.validations.annotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
