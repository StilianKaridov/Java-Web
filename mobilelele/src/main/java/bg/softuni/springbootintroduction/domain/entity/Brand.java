package bg.softuni.springbootintroduction.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Instant created;

    @Column(nullable = false)
    private Instant modified;

    @OneToMany(mappedBy = "brand", targetEntity = Model.class)
    private Set<Model> models;
}
