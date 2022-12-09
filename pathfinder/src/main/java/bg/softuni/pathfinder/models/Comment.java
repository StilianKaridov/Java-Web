package bg.softuni.pathfinder.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private Boolean approved;

    //Should not be future dates
    @Column(nullable = false)
    private LocalDateTime created;

    @Column(name = "text_content", columnDefinition = "LONGTEXT")
    private String textContent;

    @ManyToOne
    private User author;

    @ManyToOne
    private Route route;
}
