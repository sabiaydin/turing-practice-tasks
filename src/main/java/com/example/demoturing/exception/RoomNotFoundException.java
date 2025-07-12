package com.example.demoturing.exception;

public class RoomNotFoundException extends BaseException{
    public RoomNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
