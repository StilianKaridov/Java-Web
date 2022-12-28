package bg.softuni.springbootintroduction.domain.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferUpdateBindingModel {

    private Long id;

    private String modelName;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @NotEmpty
    private String imageUrl;

    @NotNull
    private int year;

    @NotNull
    private int mileage;

    @NotEmpty
    private String description;
}
