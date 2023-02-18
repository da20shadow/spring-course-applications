package shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.models.entities.Product;
import shop.services.ProductService;

import java.util.*;

@Controller
@RequestMapping("/")
public class StaticPagesController {
    private final ProductService productService;

    @Autowired
    public StaticPagesController(ProductService productService) {
        this.productService = productService;
    }

    public Map<String, List<Product>> groupProductsByCategory(List<Product> products) {
        Map<String, List<Product>> groupedProducts = new HashMap<>();

        for (Product product : products) {
            String category = product.getCategory().getName().toString();
            System.out.println("************************************");
            System.out.println("Category: " + category);
            System.out.println("************************************");
            List<Product> categoryProducts = groupedProducts.getOrDefault(category, new ArrayList<>());
            categoryProducts.add(product);
            groupedProducts.put(category, categoryProducts);
        }

        return groupedProducts;
    }

    @GetMapping
    public String showHomePage(Model model) {
        List<Product> products = this.productService.getAll();

        Map<String, List<Product>> groupedProducts = groupProductsByCategory(products);
        model.addAttribute("vegetableProducts", groupedProducts.getOrDefault("VEGETABLES", new ArrayList<>()));
        model.addAttribute("fruitsProducts", groupedProducts.getOrDefault("FRUITS", new ArrayList<>()));
        model.addAttribute("meatProducts", groupedProducts.getOrDefault("MEAT", new ArrayList<>()));
        model.addAttribute("otherProducts", groupedProducts.getOrDefault("OTHER", new ArrayList<>()));

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("404")
    public String showNotFound() {
        return "404";
    }
}
