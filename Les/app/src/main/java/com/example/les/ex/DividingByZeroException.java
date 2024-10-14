package com.example.les.ex;

public class DividingByZeroException extends Exception{
    public DividingByZeroException() {
    }

    public DividingByZeroException(String message) {
        super(message);
    }

    public DividingByZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public DividingByZeroException(Throwable cause) {
        super(cause);
    }

    public DividingByZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
