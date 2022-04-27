package com.stiches.fashionblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(NotAPostIdEx.class)
    public ResponseEntity<?> handleNotAPostEX(NotAPostIdEx ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundEx.class)
    public ResponseEntity<?> handleNotAPostEX(UserNotFoundEx ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NotAuthorized.class)
    public ResponseEntity<?> handleNotAPostEX(NotAuthorized ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotLogedInException.class)
    public ResponseEntity<ErrorDetail> handleNotLoggedInEX(NotLogedInException ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAccess.class)
    public ResponseEntity<ErrorDetail> handleNotLoggedInEX(NoAccess ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }






}
