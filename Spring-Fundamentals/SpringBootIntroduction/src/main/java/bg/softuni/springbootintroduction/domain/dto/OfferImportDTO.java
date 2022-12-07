package bg.softuni.springbootintroduction.domain.dto;

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
public class OfferImportDTO {

    private Engine engine;

    private String imageURL;

    private BigDecimal price;

    private Transmission transmission;

    private int year;

    private Instant created;

    private String model;

    private String seller;
}
