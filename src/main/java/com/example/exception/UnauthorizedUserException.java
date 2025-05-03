package com.example.exception;

//Custom exception for 401 status
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException (String message){
        super(message);
    }
}