package com.example.easy_flow_backend.error;

public class RegisterErrorException extends Exception{
    public RegisterErrorException() {
        super();
    }

    public RegisterErrorException(String message) {
        super(message);
    }

    public RegisterErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterErrorException(Throwable cause) {
        super(cause);
    }

    protected RegisterErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
