package com.example.demoturing.exception;

public class InvalidExcelDataException extends BaseException {
    public InvalidExcelDataException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
