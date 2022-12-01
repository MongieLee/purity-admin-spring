package com.example.demo.exception;

public class ServiceBusinessException extends RuntimeException{
    public ServiceBusinessException() {
    }

    public ServiceBusinessException(String message) {
        super(message);
    }

    public ServiceBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceBusinessException(Throwable cause) {
        super(cause);
    }

    public ServiceBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
