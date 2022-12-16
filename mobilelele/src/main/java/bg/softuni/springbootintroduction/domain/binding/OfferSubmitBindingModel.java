package bg.softuni.springbootintroduction.domain.binding;

import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
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

    private String model;

    private BigDecimal price;

    private Engine engine;

    private Transmission transmission;

    private int year;

    private int mileage;

    private String description;

    private String imageUrl;
}
