package bg.softuni.springbootintroduction.domain.entity;

import bg.softuni.springbootintroduction.utils.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
