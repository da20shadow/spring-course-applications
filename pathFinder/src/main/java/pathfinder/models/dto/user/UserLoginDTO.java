package pathfinder.models.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import pathfinder.validations.userValidators.ValidateLoginUser;

@ValidateLoginUser
public class UserLoginDTO {
    @NotEmpty
    @Size(min = 5,max = 45)
    private String username;
    @NotEmpty
    @Size(min = 8,max = 45)
    private String password;

    public UserLoginDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
