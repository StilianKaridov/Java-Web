package bg.softuni.springbootintroduction.domain.entity;

import bg.softuni.springbootintroduction.domain.entity.enums.Engine;
import bg.softuni.springbootintroduction.domain.entity.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Engine engine;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column
    private int mileage;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Transmission transmission;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private Instant created;

    @Column
    private Instant modified;

    @ManyToOne
    private Model model;

    @ManyToOne
    private User seller;
}
