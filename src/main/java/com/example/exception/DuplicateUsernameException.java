package com.example.exception;

//Custom exception for 409 status
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException (String message){
        super(message);
    }
}
