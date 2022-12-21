package bg.softuni.springbootintroduction.domain.binding;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferSubmitBindingModel {

    @NotEmpty
    private String model;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @NotEmpty
    private String engine;

    @NotEmpty
    private String transmission;

    @NotNull
    private int year;

    @NotNull
    private int mileage;

    @NotEmpty
    private String description;

    @NotEmpty
    private String imageUrl;
}
