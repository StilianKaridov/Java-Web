package bg.softuni.springbootintroduction.domain.view;

import bg.softuni.springbootintroduction.domain.entity.enums.Engine;
import bg.softuni.springbootintroduction.domain.entity.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferViewModel {

    private Long id;

    private String imageUrl;

    private int year;

    private String modelBrandName;

    private String modelName;

    private int mileage;

    private BigDecimal price;

    private Engine engine;

    private Transmission transmission;
}
