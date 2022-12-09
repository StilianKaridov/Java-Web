package bg.softuni.pathfinder.models;

import bg.softuni.pathfinder.models.enums.CategoryEnums;
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
@Table(name = "categories")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnums name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}
