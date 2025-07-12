package com.example.demoturing.exception;

public class RoomNotFound extends RuntimeException {
    public RoomNotFound(String message) {
        super(message);
    }
}
