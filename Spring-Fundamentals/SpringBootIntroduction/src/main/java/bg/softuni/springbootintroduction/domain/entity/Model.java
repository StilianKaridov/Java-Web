package bg.softuni.springbootintroduction.domain.entity;

import bg.softuni.springbootintroduction.utils.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "models")
public class Model extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Size(min =  8, max = 512)
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "start_year", nullable = false)
    private int startYear;

    @Column(name = "end_year")
    private int endYear;

    @Column
    private Instant created;

    @Column
    private Instant modified;

    @ManyToOne
    private Brand brand;
}
