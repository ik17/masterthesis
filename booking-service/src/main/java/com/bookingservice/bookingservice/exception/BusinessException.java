package com.bookingservice.bookingservice.exception;

public class BusinessException extends Exception {

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
