package todoapp.models.dtos;

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
public class LoginUserDTO {

    @NotBlank(message = Messages.USERNAME_REQUIRED)
    private String username;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Size(min = 8, message = Messages.MIN_PASS_LENGTH_ERR)
    private String password;
}
