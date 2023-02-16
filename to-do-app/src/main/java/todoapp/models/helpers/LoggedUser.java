package todoapp.models.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUser {

    private Long id;

    public boolean isLogged() {
        return this.id != null;
    }

    public void clearUser() {
        this.id = null;
    }
}
