package com.myspring.myproject.exception;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String message){
        super(message);
    }
}
