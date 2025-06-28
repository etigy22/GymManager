package com.gymmanager.exception;

/** Base exception for service layer errors in the gym manager application. */
public class ServiceException extends RuntimeException {

    /** Constructs a new ServiceException with the specified detail message. */
    public ServiceException(String message) {
        super(message);
    }
}
