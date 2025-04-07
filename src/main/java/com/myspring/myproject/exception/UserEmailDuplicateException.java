package com.myspring.myproject.exception;

public class UserEmailDuplicateException extends RuntimeException {
    public UserEmailDuplicateException(String message){
        super(message);
    }
}
