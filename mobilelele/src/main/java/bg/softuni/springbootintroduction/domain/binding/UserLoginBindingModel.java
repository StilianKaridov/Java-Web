package bg.softuni.springbootintroduction.domain.binding;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginBindingModel {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
