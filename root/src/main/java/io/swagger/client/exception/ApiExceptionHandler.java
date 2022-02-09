package io.swagger.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<InvalidTokenException> getErrorResponse(InvalidTokenException e){
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}
