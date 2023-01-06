package bg.softuni.springbootintroduction.domain.view;

import bg.softuni.springbootintroduction.domain.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelView {

    private String name;

    private Category category;

    private int startYear;

    private int endYear;

    private String imageUrl;
}
