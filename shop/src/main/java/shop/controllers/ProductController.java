package shop.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.models.dtos.product.ProductDTO;
import shop.models.entities.Product;
import shop.models.helpers.Option;
import shop.services.ProductService;
import shop.utils.LoggedUser;

import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final LoggedUser loggedUser;

    @Autowired
    public ProductController(ProductService productService, LoggedUser loggedUser) {
        this.productService = productService;
        this.loggedUser = loggedUser;
    }

    public double getTotalPrice(Set<Product> products) {
        double totalPrice = 0.0;

        for (Product product : products) {
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }


    @GetMapping
    public String showAllProducts(Model model) {
        if (!this.loggedUser.isLogged()) {
            return "redirect:/auth/login";
        }
        Set<Product> products = this.productService.getAllByUserId(this.loggedUser.getId());
        double totalPrice = getTotalPrice(products);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("products", products);
        return "product/all";
    }

    @GetMapping("/add")
    public String showCreateProductPage(){
        if (!this.loggedUser.isLogged()) {
            return "redirect:/auth/login";
        }
        return "product/create";
    }

    @PostMapping("/add")
    public String createProduct(@Valid ProductDTO productDTO, BindingResult result,
                                        RedirectAttributes redirectAttr) {

        if (!this.loggedUser.isLogged()) {
            return "redirect:/auth/login";
        }

        if (result.hasErrors()) {
            redirectAttr.addFlashAttribute("productDTO", productDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.productDTO", result);
            System.out.println(result);
            return "redirect:/products/add";
        }

        try {
            this.productService.createProduct(productDTO,this.loggedUser.getId());
        } catch (Exception e) {
            redirectAttr.addFlashAttribute("productDTO", productDTO)
                    .addFlashAttribute("error", e.getMessage());
            return "redirect:/products/add";
        }

        return "redirect:/products";
    }

    @GetMapping("/{id}/details")
    public String showProductDetails(@PathVariable Long id, Model model) {

        Product product = this.productService.getProductById(id);
        if (product == null){
            return "redirect:/404";
        }
        model.addAttribute("product", product);

        return "product/details";
    }

    @GetMapping("/{id}/edit")
    public String showProductEdit(@PathVariable Long id, Model model) {

        Product product = this.productService.getProductById(id);

        if (product == null || !product.getUser().getId().equals(this.loggedUser.getId())){
            return "redirect:/404";
        }

        ProductDTO productDTO = this.productService.productToProductDTO(product);
        model.addAttribute("productDTO", productDTO);

        List<Option> options = Arrays.asList(
                new Option("FRUITS", "Fruits"),
                new Option("VEGETABLES", "Vegetables"),
                new Option("MEAT", "Meat"),
                new Option("OTHER", "Other")
        );

        String selected = productDTO.getCategory().toString();

        model.addAttribute("options", options);
        model.addAttribute("selectedValue", selected);

        return "product/edit";
    }

    @PostMapping("/{id}/edit")
    public String showProductEdit(@PathVariable Long id, @Valid ProductDTO productDTO, BindingResult result,
                                  RedirectAttributes redirectAttr) {

        if (!this.loggedUser.isLogged()) {
            return "redirect:/404";
        }

        if (result.hasErrors()) {
            redirectAttr.addFlashAttribute("productDTO", productDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.productDTO", result);
            System.out.println(result);
            return "redirect:/products/" + id + "/edit";
        }

        try {
            this.productService.updateProduct(productDTO, id ,this.loggedUser.getId());
        } catch (Exception e) {
            redirectAttr.addFlashAttribute("productDTO", productDTO)
                    .addFlashAttribute("error", e.getMessage());
            return "redirect:/products/" + id + "/edit";
        }

        return "redirect:/products/" + id + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {

        Product product = this.productService.getProductById(id);

        if (!this.loggedUser.isLogged() || !product.getUser().getId().equals(this.loggedUser.getId())) {
            return "redirect:/404";
        }

        try {
            this.productService.deleteProduct(id);
        } catch (Exception e) {
            return "redirect:/products/" + id + "/details";
        }

        return "redirect:/products";
    }

    //Model Attributes
    @ModelAttribute
    public void addLoggedUserAttribute(Model model) {
        model.addAttribute("loggedUser");
    }
    @ModelAttribute
    public ProductDTO productDTO(){
        return new ProductDTO();
    }
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("error");
        model.addAttribute("productDTO");
    }
}
