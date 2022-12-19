package bg.softuni.springbootintroduction.domain.binding;

import bg.softuni.springbootintroduction.domain.validation.UniqueUsername;
import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferSubmitBindingModel {

    private final int CURRENT_YEAR = LocalDate.now().getYear();

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
