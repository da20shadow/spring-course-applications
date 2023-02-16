package pathfinder.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pathfinder.models.dto.user.EditProfileDTO;
import pathfinder.models.dto.user.UserLoginDTO;
import pathfinder.models.dto.user.UserRegisterDTO;
import pathfinder.models.entity.User;
import pathfinder.services.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    private String getLoginPage(@ModelAttribute("userLoginForm") UserLoginDTO userLoginForm,
                                Model model, @CookieValue(name = "username",defaultValue = "")
            String username) {

        if (!username.equals("")){
            model.addAttribute("user", username);
            return "redirect:profile";
        }
        model.addAttribute("error", "");
        return "login";
    }

    @PostMapping("/login")
    private String postLoginPage(HttpServletResponse response,
                                 @ModelAttribute("userLoginForm") UserLoginDTO userLoginForm,
                                 Model model) {

        var result = this.userService.login(userLoginForm.getUsername(),userLoginForm.getPassword());

        if (result == null){
            model.addAttribute("error", "Bad credentials!");
            return "login";
        }

        final Cookie cookie = new Cookie("username",result.getUsername());
        model.addAttribute("user", result);
        response.addCookie(cookie);

        return "redirect:profile";
    }

    @GetMapping("/register")
    private String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    private String postRegisterPage(@Valid @ModelAttribute("userRegForm") UserRegisterDTO userRegForm,
                                    Model model, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegForm",userRegForm)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegForm",bindingResult);
        }

        var resultUsername = this.userService.getUserByUsername(userRegForm.getUsername());
        var resultEmail = this.userService.getUserByEmail(userRegForm.getEmail());

        if (resultUsername != null || resultEmail != null){
            model.addAttribute("error", "Such username already registered!");
            return "register";
        }

        User user = this.userService.convertToEntity(userRegForm);
        this.userService.register(user);

        return "redirect:login";
    }

    @GetMapping("/profile")
    private String getProfilePage(Model model,
                                  @CookieValue(name = "username",defaultValue = "")
                                          String username) {
        if (username.equals("")){
            return "redirect:login";
        }
        User tempUser = this.userService.getUserByUsername(username);
        model.addAttribute("user", tempUser);
        return "profile";
    }

    @GetMapping("/edit-profile")
    private String getEditProfilePage(Model model,
                                      @CookieValue(name = "username",defaultValue = "")
                                              String username) {
        if (username.equals("")){
            return "redirect:login";
        }
        User tempUser = this.userService.getUserByUsername(username);
        model.addAttribute("user", tempUser);
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    private String postEditProfilePage(@ModelAttribute EditProfileDTO editProfileDTO) {
        //TODO: implement update profile
        System.out.println("New birth date: " + editProfileDTO.getAge());
        System.out.println("New email: " + editProfileDTO.getEmail());
        System.out.println("New pass: " + editProfileDTO.getPassword());
        return "edit-profile";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        final Cookie cookie = new Cookie("username","");
        response.addCookie(cookie);
        return "redirect:login";
    }

    //Model attributes
    @ModelAttribute(name = "userRegForm")
    public UserRegisterDTO initUserRegFormDto(){
        return new UserRegisterDTO();
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
