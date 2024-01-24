package com.br93.testbackend.exception;

public class BucketNotFoundException extends RuntimeException{

    public BucketNotFoundException(String message){
        super(message);
    }
    
}
