package todoapp.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todoapp.constants.Messages;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    @NotBlank(message = Messages.USERNAME_REQUIRED)
    private String username;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Size(min = 8, message = Messages.MIN_PASS_LENGTH_ERR)
    private String password;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Size(min = 8, message = Messages.MIN_PASS_LENGTH_ERR)
    private String rePassword;

    @NotBlank(message = Messages.FIRST_NAME_REQUIRED)
    private String firstName;

    @NotBlank(message = Messages.LAST_NAME_REQUIRED)
    private String lastName;

    @NotBlank(message = Messages.EMAIL_REQUIRED)
    @Email(message = Messages.EMAIL_INVALID)
    private String email;
}
