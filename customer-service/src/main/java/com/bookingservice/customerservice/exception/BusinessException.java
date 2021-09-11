package com.bookingservice.customerservice.exception;

public class BusinessException extends Exception {

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
