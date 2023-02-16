package pathfinder.validations.userValidators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import pathfinder.models.dto.user.UserLoginDTO;
import pathfinder.models.entity.User;
import pathfinder.repositories.UserRepository;

import java.util.Optional;

public record LoginUserValidator(
        UserRepository userRepository) implements ConstraintValidator<ValidateLoginUser, UserLoginDTO> {

    @Override
    public void initialize(ValidateLoginUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserLoginDTO userLoginDTO, ConstraintValidatorContext context) {
        Optional<User> result =
                this.userRepository
                        .findOneByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        return result.isPresent();
    }
}
