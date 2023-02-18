package exam2023.models.dtos.user;

import exam2023.constants.Messages;
import exam2023.validations.annotations.UniqueEmail;
import exam2023.validations.annotations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Size(min = 3, max = 20, message = Messages.MIN_PASS_LENGTH_ERR)
    private String password;

    @NotBlank
    private String confirmPassword;
}
