package com.stiches.fashionblog.exception;

public class NotLogedInException extends RuntimeException{
    public NotLogedInException(String message){
        super(message);
    }
}
