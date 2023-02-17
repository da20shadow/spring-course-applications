package shop.models.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.constants.Messages;
import shop.validations.annotations.UniqueEmail;
import shop.validations.annotations.UniqueUsername;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {

    @UniqueUsername
    @NotBlank(message = Messages.USERNAME_REQUIRED)
    @Size(min = 3, max = 20, message = Messages.USERNAME_INVALID_LENGTH)
    private String username;

    @UniqueEmail
    @NotBlank(message = Messages.EMAIL_REQUIRED)
    @Email(message = Messages.EMAIL_INVALID)
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{2,}[.][a-z]{2,9}$",
            message = Messages.EMAIL_INVALID)
    private String email;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Size(min = 8, message = Messages.MIN_PASS_LENGTH_ERR)
    private String password;

    @NotBlank
    private String confirmPassword;
}
