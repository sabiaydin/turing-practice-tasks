package com.example.demoturing.exception;

public class HotelNotFoundException extends BaseException {
    public HotelNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
