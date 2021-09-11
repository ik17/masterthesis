package com.bookingservice.ticketingservice.exception;

public class BusinessException extends Exception {
    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
