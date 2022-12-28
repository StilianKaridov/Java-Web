package bg.softuni.springbootintroduction.domain.binding;

import bg.softuni.springbootintroduction.domain.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterBindingModel {

    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastName;

    @NotEmpty(message = "Username is required.")
    @UniqueUsername(message = "The username is already in use.")
    private String username;

    @NotEmpty
    @Size(min = 5)
    private String password;
}
