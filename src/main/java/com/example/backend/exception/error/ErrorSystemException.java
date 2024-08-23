package com.example.backend.exception.error;

public class ErrorSystemException extends RuntimeException{
    public ErrorSystemException() {
    }

    public ErrorSystemException(String message) {
        super(message);
    }

    public ErrorSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorSystemException(Throwable cause) {
        super(cause);
    }

    public ErrorSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
