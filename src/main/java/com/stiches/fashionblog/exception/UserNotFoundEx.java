package com.stiches.fashionblog.exception;

public class UserNotFoundEx extends RuntimeException{
    public UserNotFoundEx(String message){
        super(message);
    }
}
