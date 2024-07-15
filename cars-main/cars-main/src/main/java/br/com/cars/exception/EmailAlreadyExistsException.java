package br.com.cars.exception;

import br.com.cars.exception.config.ApiException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException {

    public EmailAlreadyExistsException(){
        super("Email already exists", 2, HttpStatus.BAD_REQUEST);
    }

    public EmailAlreadyExistsException(String message){
        super(message, 2, HttpStatus.BAD_REQUEST);
    }
}
