package likebook.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import likebook.service.UserService;
import likebook.validation.annotations.UniqueEmail;

public record UniqueEmailValidator(
        UserService userService) implements ConstraintValidator<UniqueEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.findUserByEmail(value) == null;
    }
}