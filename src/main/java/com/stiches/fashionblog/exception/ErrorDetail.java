package com.stiches.fashionblog.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetail {
    private Date timeStamp;
    private String message;
    private String details;

    public ErrorDetail(String message, String details){
        this.message =message;
        this.details = details;
        this.timeStamp = new Date();
    }
}
