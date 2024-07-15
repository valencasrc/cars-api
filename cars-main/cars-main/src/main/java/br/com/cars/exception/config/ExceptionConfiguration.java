package br.com.cars.exception.config;

import br.com.cars.exception.InvalidTokenException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionConfiguration {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> errorHandler(ApiException e){
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getMessage(), e.getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> errorHandler(MethodArgumentNotValidException e){
        Optional<FieldError> error = e.getBindingResult().getFieldErrors().stream().findFirst();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(error.get().getDefaultMessage(), 5));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> errorHandler(MissingPathVariableException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), 4));
    }
}
