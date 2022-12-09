package bg.softuni.pathfinder.models;

import bg.softuni.pathfinder.models.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Length(min = 2)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Length(min = 2)
    @Column(nullable = false)
    private String username;

    @Length(min = 2)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    //Must contain @ and .
    @Column
    private String email;

    //Each registered user should have 'USER' role
    @ManyToMany
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;
}
