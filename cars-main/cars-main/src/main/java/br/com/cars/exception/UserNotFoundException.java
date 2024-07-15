package br.com.cars.exception;

import br.com.cars.exception.config.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(){
        super("User not found", 6, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String message){
        super(message, 6, HttpStatus.NOT_FOUND);
    }
}
