package net.lahlalia.produit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegimeNotFoundException extends RuntimeException {
    public RegimeNotFoundException(String message) {
        super(message);
    }
}
