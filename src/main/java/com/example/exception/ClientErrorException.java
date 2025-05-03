package com.example.exception;

//Custom exception for 400 status
public class ClientErrorException extends RuntimeException {
    public ClientErrorException (String message){
        super(message);
    }
}
