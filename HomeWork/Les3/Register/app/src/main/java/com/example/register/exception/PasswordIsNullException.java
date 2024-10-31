package com.example.register.exception;

public class PasswordIsNullException extends RuntimeException{
    public PasswordIsNullException() {
    }

    public PasswordIsNullException(String message) {
        super(message);
    }

    public PasswordIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordIsNullException(Throwable cause) {
        super(cause);
    }

    public PasswordIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
