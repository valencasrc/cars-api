package br.com.cars.exception;

import br.com.cars.exception.config.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SignInInvalidException extends ApiException {

    public SignInInvalidException(){
        super("Invalid login or password", 1, HttpStatus.BAD_REQUEST);
    }

    public SignInInvalidException(String message){
        super(message, 1, HttpStatus.BAD_REQUEST);
    }
}
