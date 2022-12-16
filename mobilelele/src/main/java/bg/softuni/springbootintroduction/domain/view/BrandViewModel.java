package bg.softuni.springbootintroduction.domain.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandViewModel {

    private String name;

    private Set<ModelView> models;
}
