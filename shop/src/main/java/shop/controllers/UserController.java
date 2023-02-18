package shop.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.models.entities.Product;
import shop.models.entities.User;
import shop.services.UserService;
import shop.utils.LoggedUser;

import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final LoggedUser loggedUser;
    private final UserService userService;

    @Autowired
    public UserController(LoggedUser loggedUser, UserService userService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
    }

    @GetMapping("/profile")
    @Transactional
    public String showProfile(Model model){
        User user = this.userService.getProfileInfo(this.loggedUser.getId());
        if (user == null) {
            return "redirect:/auth/login";
        }
        try {
            Set<Product> products = user.getProducts();
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/edit-profile")
    public String showEditProfile(){
        return "user/edit-profile";
    }

    //Model Attributes
    @ModelAttribute
    public void addLoggedUserAttribute(Model model) {
        model.addAttribute("loggedUser");
    }
}
