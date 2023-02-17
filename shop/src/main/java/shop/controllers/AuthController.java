package shop.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.models.dtos.user.LoginUserDTO;
import shop.models.dtos.user.RegisterUserDTO;
import shop.services.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid LoginUserDTO loginUserDTO, BindingResult result,
                               RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginUserDTO", loginUserDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.loginUserDTO", result);
            return "redirect:/auth/login";
        }

        try {
            this.authService.login(loginUserDTO);
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("loginUserDTO", loginUserDTO)
                    .addFlashAttribute("userLogged", false)
                    .addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid RegisterUserDTO registerUserDTO, BindingResult result,
                                  RedirectAttributes redirectAttr){

        if (result.hasErrors()) {
            redirectAttr.addFlashAttribute("registerUserDTO",registerUserDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.registerUserDTO", result);
            return "redirect:/auth/register";
        }

        try {
            this.authService.register(registerUserDTO);
            return "redirect:/auth/login";
        } catch (Exception e) {
            redirectAttr.addFlashAttribute("registerUserDTO",registerUserDTO)
                    .addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        this.authService.logout();
        return "redirect:/";
    }

    //Model Attributes
    @ModelAttribute
    public LoginUserDTO loginUserDTO() {
        return new LoginUserDTO();
    }

    @ModelAttribute
    public RegisterUserDTO registerUserDTO() {
        return new RegisterUserDTO();
    }

    @ModelAttribute
    public void addLoggedUserAttribute(Model model) {
        model.addAttribute("loggedUser");
    }

    @ModelAttribute
    public void addErrorAttribute(Model model) {
        model.addAttribute("error");
    }
}
