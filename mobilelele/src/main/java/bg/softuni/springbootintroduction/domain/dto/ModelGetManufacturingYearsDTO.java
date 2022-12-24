package bg.softuni.springbootintroduction.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelGetManufacturingYearsDTO {

    private String name;

    private int startYear;

    private int endYear;
}
