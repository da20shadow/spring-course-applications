package shop.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@Component
@SessionScope
public class LoggedUser {
    private Long id;
    private String username;
    public void clear(){
        setId(null);
        setUsername(null);
    }
    public boolean isLogged() {
        return this.username != null && this.id != null;
    }
}
