package shop.models.dtos.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.models.enums.CategoryEnum;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product name can not be empty!")
    @Size(min = 3, message = "Product name must be at least 3 characters long!")
    private String name;

    @Min(value = 0,message = "Product price can not be cheaper than $1 USD!")
    private Double price;

    @NotBlank
    @Size(min = 3, message = "Product description must be at least 3 characters long!")
    private String description;

    private CategoryEnum category;

    @NotBlank
    private String expirationDate;
}
