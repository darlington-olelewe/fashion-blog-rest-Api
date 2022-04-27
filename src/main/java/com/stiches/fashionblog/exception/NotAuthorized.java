package com.stiches.fashionblog.exception;

public class NotAuthorized extends RuntimeException{
    public NotAuthorized(String message){
        super(message);
    }
}
