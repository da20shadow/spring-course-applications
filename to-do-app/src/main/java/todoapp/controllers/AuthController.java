package todoapp.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todoapp.constants.Environments;
import todoapp.models.dtos.LoginUserDTO;
import todoapp.models.dtos.RegisterUserDTO;
import todoapp.models.entities.User;
import todoapp.services.AuthService;
import todoapp.services.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid LoginUserDTO loginUserForm, Model model,
                               @CookieValue(name = "username", defaultValue = "") String username,
                               HttpServletResponse response, HttpServletRequest request) {

        if (!username.equals("")) {
            model.addAttribute("username", username);
            return "redirect:profile";
        }

        try {
            User user = authService.login(loginUserForm);
            Cookie cookie = new Cookie("username", user.getUsername());
            cookie.setMaxAge(60 * 60 * 2); // set cookie to expire in 2 hours
            cookie.setDomain(request.getServerName()); // set the cookie domain
            cookie.setPath("/"); // set the cookie path to the root path of the site
            response.addCookie(cookie);
            return "redirect:/profile";

        } catch (Exception exception) {
            model.addAttribute("error", exception.getMessage());
            return "user/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String processRegistration(RegisterUserDTO registerUserForm, Model model) {
        try {
            this.authService.register(registerUserForm);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/register";
        }
        return "redirect:/auth/login";
    }

    //Model attributes
    @ModelAttribute(name = "registerUserForm")
    public RegisterUserDTO initUserRegFormDto() {
        return new RegisterUserDTO();
    }

    @ModelAttribute(name = "loginUserForm")
    public LoginUserDTO initUserLoginFormDto() {
        return new LoginUserDTO();
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
