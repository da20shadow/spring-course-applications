package pathfinder.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class EditProfileDTO {
    @Email
    private String email;
    @Min(0)
    private String age;
    @Size(min = 8, max = 75)
    private String password;

    public EditProfileDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
