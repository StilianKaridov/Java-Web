package bg.softuni.springbootintroduction.service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Offer not found!")
public class OfferNotFoundException extends RuntimeException{

    private Long id;

    public OfferNotFoundException(Long id) {
        super("Cannot find offer with id " + id);
        this.id = id;
    }
}
