package bg.softuni.springbootintroduction.domain.view;

import bg.softuni.springbootintroduction.utils.enums.Engine;
import bg.softuni.springbootintroduction.utils.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDetailsViewModel {

    private Long id;

    private String imageUrl;

    private int year;

    private String modelBrandName;

    private String modelName;

    private int mileage;

    private BigDecimal price;

    private Engine engine;

    private Transmission transmission;

    private Instant created;

    private Instant modified;

    private String sellerUsername;

    private String description;
}
