package shop.models.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.constants.Messages;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {

    @NotBlank(message = Messages.USERNAME_REQUIRED)
    @Size(min = 3, max = 20,message = Messages.INVALID_CREDENTIALS)
    private String username;

    @NotBlank(message = Messages.PASSWORD_REQUIRED)
    @Size(min = 8, message = Messages.INVALID_CREDENTIALS)
    private String password;
}
