package com.bookingservice.customerservice.exception;

public class ServiceException extends Exception{

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
