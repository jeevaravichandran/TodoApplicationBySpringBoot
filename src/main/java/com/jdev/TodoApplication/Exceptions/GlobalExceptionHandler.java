package com.jdev.TodoApplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String,Object>> exceptionHandler(MethodArgumentNotValidException exception){

        List<Map<String,String>> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String,String> errorDetails = new HashMap<>();
            errorDetails.put("field", error.getField());
            errorDetails.put("message",error.getDefaultMessage());
            errors.add(errorDetails);

        });

        Map<String,Object> response = new HashMap<>();
        response.put("errors",errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", new Date());

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundExceptionHandler(UsernameNotFoundException exception){
        return new ResponseEntity<>("UserName Not Found", HttpStatus.UNAUTHORIZED);
    }
}
