package com.example.rest_service.exception;

public class InvalidInputTypeException extends RuntimeException {

    private String message;

    public InvalidInputTypeException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
