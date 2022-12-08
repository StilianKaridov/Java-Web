package bg.softuni.springbootintroduction.domain.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterBindingModel {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String role;
}
