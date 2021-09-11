package com.bookingservice.ticketingservice.exception;

public class ServiceException extends Exception {
    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
