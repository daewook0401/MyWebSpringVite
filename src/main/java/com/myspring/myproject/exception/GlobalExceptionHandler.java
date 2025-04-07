package com.myspring.myproject.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<?> makeResponseEntity(RuntimeException e, HttpStatus status){
        Map<String, String> error = new HashMap();
        error.put("error-message", e.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserEmailDuplicateException.class)
    public ResponseEntity<?> handleUserIdDuplicateException(UserEmailDuplicateException e){
        return makeResponseEntity(e, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> handleCustomAuthenticationException(CustomAuthenticationException e){
        return makeResponseEntity(e, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidUserREquestException.class)
    public ResponseEntity<?> handleInvalidUserREquestException(InvalidUserREquestException e){
        return makeResponseEntity(e, HttpStatus.BAD_REQUEST);
    }
}
