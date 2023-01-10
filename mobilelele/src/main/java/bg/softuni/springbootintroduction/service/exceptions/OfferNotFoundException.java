package bg.softuni.springbootintroduction.service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Offer not found!")
public class OfferNotFoundException extends RuntimeException{

    private static final String EXCEPTION_MESSAGE = "Cannot find offer with id ";

    private Long id;

    public OfferNotFoundException(Long id) {
        super(EXCEPTION_MESSAGE + id);
        this.id = id;
    }
}
