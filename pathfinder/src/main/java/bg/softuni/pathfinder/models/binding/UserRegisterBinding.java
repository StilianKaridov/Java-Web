package bg.softuni.pathfinder.models.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterBinding {

    private String username;

    private String fullName;

    private String email;

    private Integer age;

    private String password;

    private String confirmPassword;
}
