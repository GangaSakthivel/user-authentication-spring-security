package com.security_jwt.exceptions;

public class PhoneNumberNotFound extends RuntimeException{
    public PhoneNumberNotFound(String message){
        super(message);
    }
}
