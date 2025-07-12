package com.example.demoturing.exception;

public class HotelNotFound extends RuntimeException {
    public HotelNotFound(String message) {
        super(message);
    }
}
