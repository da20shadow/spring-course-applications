package pathfinder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pathfinder.models.dto.route.view.MostCommentedRouteViewDTO;
import pathfinder.models.entity.User;
import pathfinder.services.RouteService;
import pathfinder.services.UserService;

import java.util.Date;

@Controller
@RequestMapping("/")
public class StaticPageController {

    private final RouteService routeService;
    private final UserService userService;

    @Autowired
    public StaticPageController(RouteService routeService, UserService userService) {
        this.routeService = routeService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        Date todayDate = new Date();
        model.addAttribute("todayDate", todayDate);

        try {
            final MostCommentedRouteViewDTO mostCommentedRouteViewDTO =
                    routeService.getMostCommented();
            model.addAttribute("mostCommented", mostCommentedRouteViewDTO);
            System.out.println(mostCommentedRouteViewDTO.getName());
            System.out.println(mostCommentedRouteViewDTO.getDescription());
        } catch (Exception exception) {
            model.addAttribute("error", "No Routes yet!");
            System.out.println("No Routes Yet!");
        }
        return "index";
    }

    @GetMapping("about")
    public String getAboutPage(Model model) {
        return "about";
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
