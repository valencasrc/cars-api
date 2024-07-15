package br.com.cars.exception;

import br.com.cars.exception.config.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException {

    public InvalidTokenException(){
        super("Unauthorized - invalid session", 2, HttpStatus.FORBIDDEN);
    }

    public InvalidTokenException(String message){
        super(message, 2, HttpStatus.FORBIDDEN);
    }
}
