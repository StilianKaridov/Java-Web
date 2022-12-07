package bg.softuni.springbootintroduction.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandImportDTO {

    private String name;

    private Instant created;

    private Instant modified;
}
