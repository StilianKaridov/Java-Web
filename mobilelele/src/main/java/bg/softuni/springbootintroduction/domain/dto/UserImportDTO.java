package bg.softuni.springbootintroduction.domain.dto;

import bg.softuni.springbootintroduction.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserImportDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private Role role;

    private Instant created;
}
