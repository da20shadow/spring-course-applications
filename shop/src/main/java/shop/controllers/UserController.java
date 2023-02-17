package shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/profile")
    public String showProfile(){
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
