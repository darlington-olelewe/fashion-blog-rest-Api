package com.stiches.fashionblog.exception;

public class NotAUser extends RuntimeException{
    public NotAUser(String message){
        super(message);
    }
}
