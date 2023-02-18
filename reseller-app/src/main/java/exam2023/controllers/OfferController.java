package exam2023.controllers;

import exam2023.models.dtos.offer.OfferDTO;
import exam2023.models.entities.Offer;
import exam2023.models.entities.User;
import exam2023.services.OfferService;
import exam2023.services.UserService;
import exam2023.utils.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
public class OfferController {

    private final LoggedUser loggedUser;
    private final OfferService offerService;
    private final UserService userService;

    @Autowired
    public OfferController(LoggedUser loggedUser, OfferService offerService, UserService userService) {
        this.loggedUser = loggedUser;
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomePAge() {
        return "index";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        if (!this.loggedUser.isLogged()) {
            return "redirect:/auth/login";
        }

        try {
            List<Offer> allOffers = this.offerService.getUnpurchasedOffersFromOtherUsers(this.loggedUser.getId());
            model.addAttribute("allOffers", allOffers);
            model.addAttribute("totalOffers", allOffers.size());

            User user = this.userService.getUserById(this.loggedUser.getId());

            Set<Offer> myOffers = user.getOffers();
            model.addAttribute("myOffers", myOffers);

            Set<Offer> boughtOffers = user.getBoughtOffers();
            model.addAttribute("boughtOffers", boughtOffers);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "home";
    }

    @PostMapping("/offers/{offerId}/buy")
    public String buyOffer(@PathVariable Long offerId) {
        if (!this.loggedUser.isLogged()) {
            return "redirect:/404";
        }
        try {
            this.offerService.buyOffer(offerId,this.loggedUser.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "redirect:/home";
    }

    @PostMapping("/offers/{offerId}/remove")
    public String removeOffer(@PathVariable Long offerId) {
        if (!this.loggedUser.isLogged()) {
            return "redirect:/404";
        }
        try {
            this.offerService.deleteOffer(offerId);
        } catch (Exception e) {
            System.out.println("************************************");
            System.out.println("Error: " + e.getMessage());
            System.out.println("************************************");
        }
        return "redirect:/home";
    }

    @GetMapping("/add-offer")
    public String showAddOfferPage() {
        if (!this.loggedUser.isLogged()) {
            return "redirect:/auth/login";
        }
        return "offer-add";
    }

    @PostMapping("/add-offer")
    public String addOffer(@Valid OfferDTO offerDTO, BindingResult result,
                           RedirectAttributes redirectAttr){

        if (!this.loggedUser.isLogged()) {
            return "redirect:/auth/login";
        }

        if (result.hasErrors()) {
            redirectAttr.addFlashAttribute("offerDTO", offerDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.offerDTO", result);
            System.out.println(result);
            return "redirect:/add-offer";
        }

        try {
            this.offerService.createOffer(offerDTO,this.loggedUser.getId());
        } catch (Exception e) {
            redirectAttr.addFlashAttribute("offerDTO", offerDTO)
                    .addFlashAttribute("error", e.getMessage());
            return "redirect:/add-offer";
        }

        return "redirect:/home";
    }

    //Model Attributes
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("loggedUser");
        model.addAttribute("productDTO");
        model.addAttribute("allOffers");
        model.addAttribute("myOffers");
        model.addAttribute("boughtOffers");
    }
    @ModelAttribute
    public OfferDTO productDTO(){
        return new OfferDTO();
    }
}
