package pathfinder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pathfinder.models.entity.User;
import pathfinder.services.UserService;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final UserService userService;

    public RouteController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRoutesPage() {
        return "routes";
    }

    @GetMapping("/add")
    public String getAddRoutePage(Model model) {
        return "add-route";
    }

    @PostMapping("/add")
    public String postAddRoutePage() {
        return "redirect:add-route";
    }

    //Model Attributes
    @ModelAttribute(name = "user")
    public User getLoggedUser(@CookieValue(name = "username",defaultValue = "")
                                      String username){
        if (username.equals("")){
            return null;
        }
        return this.userService.getUserByUsername(username);
    }

}
