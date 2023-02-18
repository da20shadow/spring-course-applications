package shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.models.entities.Product;
import shop.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/")
public class StaticPagesController {
    private final ProductService productService;

    @Autowired
    public StaticPagesController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showHomePage(Model model) {
        List<Product> products = this.productService.getAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("404")
    public String showNotFound() {
        return "404";
    }
}
