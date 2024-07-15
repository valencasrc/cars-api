package br.com.cars.exception;

import br.com.cars.exception.config.ApiException;
import org.springframework.http.HttpStatus;

public class CarNotFoundException extends ApiException {

    public CarNotFoundException(){
        super("Car not found", 6, HttpStatus.NOT_FOUND);
    }

    public CarNotFoundException(String message){
        super(message, 6, HttpStatus.NOT_FOUND);
    }
}
