package com.ua.foxminded.task_11.exceptions;



public class WrongInputDataException extends ServicesException {
    public WrongInputDataException() {
    }

    public WrongInputDataException(String message) {
        super(message);
    }

    public WrongInputDataException(String message, Exception cause) {
        super(message, cause);
    }

}
