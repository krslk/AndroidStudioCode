package com.example.les.ex;

public class OperandFormatException extends Exception{
    public OperandFormatException() {
    }

    public OperandFormatException(String message) {
        super(message);
    }

    public OperandFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperandFormatException(Throwable cause) {
        super(cause);
    }

    public OperandFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
