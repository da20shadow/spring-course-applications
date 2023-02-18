package shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.exceptions.BadRequestException;
import shop.models.dtos.product.ProductDTO;
import shop.models.entities.Category;
import shop.models.entities.Product;
import shop.models.entities.User;
import shop.repositories.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService, UserService userService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public void createProduct(ProductDTO productDTO,Long userId) {
        Product product = this.productDTOtoProduct(productDTO,userId);
        this.productRepository.save(product);
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public Set<Product> getAllByUserId(Long id) {
        return this.productRepository.getAllByUserId(id);
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public ProductDTO productToProductDTO(Product product) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = product.getExpirationDate().format(formatter);
        productDTO.setExpirationDate(date);

        productDTO.setCategory(product.getCategory().getName());
        return productDTO;
    }

    private Product productDTOtoProduct(ProductDTO productDTO,Long userId) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'[HH:mm[:ss][.SSS][.SS][.S]]]");
        LocalDateTime dateTime;
        if (productDTO.getExpirationDate().contains("T")) {
            dateTime = LocalDateTime.parse(productDTO.getExpirationDate(), formatter);
        } else {
            LocalDate date = LocalDate.parse(productDTO.getExpirationDate(), formatter);
            dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        }
        product.setExpirationDate(dateTime);

        Category category = this.categoryService.getCategoryByName(productDTO.getCategory());
        if (category == null) {
            throw new BadRequestException("Invalid Category!");
        }
        product.setCategory(category);

        User user = this.userService.getUserById(userId);
        if (user == null) {
            throw new BadRequestException("Invalid User!");
        }
        product.setUser(user);
        return product;
    }

    public void updateProduct(ProductDTO productDTO, Long productId, Long userId) {
        Product product = this.productRepository.findById(productId).orElse(null);
        if (product == null || !product.getUser().getId().equals(userId)) {
            throw new BadRequestException("Invalid Request!");
        }
        if (!product.getName().equals(productDTO.getName())){
            product.setName(productDTO.getName());
        }
        if (!product.getDescription().equals(productDTO.getDescription())) {
            product.setDescription(productDTO.getDescription());
        }
        if (!product.getPrice().equals(productDTO.getPrice())) {
            product.setPrice(productDTO.getPrice());
        }
        if (!product.getCategory().getName().equals(productDTO.getCategory())) {
            Category category = this.categoryService.getCategoryByName(productDTO.getCategory());
            product.setCategory(category);
        }

        String oldProductDate = this.convertLocalDateTimeToHtmlDateInput(product.getExpirationDate());
        String updatedProductDate = productDTO.getExpirationDate();
        if (!oldProductDate.equals(updatedProductDate)) {
            LocalDateTime newLocalDate = this.convertHtmlDateInputToLocalDateTime(updatedProductDate);
            product.setExpirationDate(newLocalDate);
        }

        this.productRepository.save(product);
    }

    public LocalDateTime convertHtmlDateInputToLocalDateTime(String htmlDateInput) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'[HH:mm[:ss][.SSS][.SS][.S]]]");
        LocalDateTime dateTime;
        if (htmlDateInput.contains("T")) {
            dateTime = LocalDateTime.parse(htmlDateInput, formatter);
        } else {
            LocalDate date = LocalDate.parse(htmlDateInput, formatter);
            dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        }
        return dateTime;
    }

    public String convertLocalDateTimeToHtmlDateInput(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }
}
