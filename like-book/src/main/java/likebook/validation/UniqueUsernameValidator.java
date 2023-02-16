package likebook.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import likebook.service.UserService;
import likebook.validation.annotations.UniqueUsername;

public record UniqueUsernameValidator(
        UserService userService) implements ConstraintValidator<UniqueUsername, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.findUserByUsername(value) == null;
    }
}
