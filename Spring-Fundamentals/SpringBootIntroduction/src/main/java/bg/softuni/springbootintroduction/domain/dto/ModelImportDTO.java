package bg.softuni.springbootintroduction.domain.dto;

import bg.softuni.springbootintroduction.utils.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelImportDTO {

    private String name;

    private Category category;

    private int startYear;

    private String brand;
}
