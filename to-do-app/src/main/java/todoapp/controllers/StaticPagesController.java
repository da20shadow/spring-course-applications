package todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import todoapp.models.entities.User;
import todoapp.services.UserService;

@Controller("/")
public class StaticPagesController {

    private final UserService userService;

    @Autowired
    public StaticPagesController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showHomePage(){
        return "index";
    }

    @ModelAttribute(name = "username")
    public User getLoggedUser(@CookieValue(name = "username", defaultValue = "")
                                      String username) {
        if (username.equals("")) {
            return null;
        }
        return this.userService.getUserByUsername(username);
    }
}
