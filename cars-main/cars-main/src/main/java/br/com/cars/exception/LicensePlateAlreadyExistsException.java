package br.com.cars.exception;

import br.com.cars.exception.config.ApiException;
import org.springframework.http.HttpStatus;

public class LicensePlateAlreadyExistsException extends ApiException {

    public LicensePlateAlreadyExistsException(){
        super("License plate already exists", 3, HttpStatus.BAD_REQUEST);
    }
    public LicensePlateAlreadyExistsException(String message){
        super(message, 3, HttpStatus.BAD_REQUEST);
    }
}
