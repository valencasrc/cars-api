package br.com.cars.exception.config;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private Integer code;

    private HttpStatus status;

    public ApiException(String message, Integer code,  HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
