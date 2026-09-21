package com.devi.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
System.out.println("Exception occurred: ");
        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
 @ExceptionHandler(MethodArgumentNotValidException.class)//Validation fail spring
public ResponseEntity<String> handleValidationException(
        MethodArgumentNotValidException ex) {

    String error = ex.getBindingResult()
                     .getFieldError()
                     .getDefaultMessage();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    
}

