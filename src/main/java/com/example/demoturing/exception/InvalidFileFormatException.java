package com.example.demoturing.exception;

public class InvalidFileFormatException extends BaseException {
    public InvalidFileFormatException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

