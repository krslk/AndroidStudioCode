package com.example.les.ex;

public class TailSpareSignException extends Exception {
    public TailSpareSignException() {
    }

    public TailSpareSignException(String message) {
        super(message);
    }

    public TailSpareSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public TailSpareSignException(Throwable cause) {
        super(cause);
    }

    public TailSpareSignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
