package com.ua.foxminded.task_11.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException() {
    }


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
