package exam2023.models.dtos.offer;

import exam2023.models.enums.ConditionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {

    @Positive(message = "Offer price can not be less than $0.00 USD!")
    @NotNull(message = "Price can not be empty!")
    private Double price;

    @NotBlank(message = "Offer description can not be empty!")
    @Size(min = 2,max = 50,message = "Offer description must be at least 2 and max 50 characters long!")
    private String description;

    @NotNull(message = "Please, select condition!")
    private ConditionEnum condition;
}
