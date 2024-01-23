package com.br93.testbackend.exception;

public class ProductInvalidException extends RuntimeException{

    public ProductInvalidException(String message){
        super(message);
    }
    
}
