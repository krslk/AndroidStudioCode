package com.example.register.exception;

public class UsernameIsNullException extends RuntimeException{
    public UsernameIsNullException() {
    }

    public UsernameIsNullException(String message) {
        super(message);
    }

    public UsernameIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameIsNullException(Throwable cause) {
        super(cause);
    }

    public UsernameIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
