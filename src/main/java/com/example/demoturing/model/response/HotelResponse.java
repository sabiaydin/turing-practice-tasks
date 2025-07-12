package com.example.demoturing.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelResponse {
    private String name;
    private String location;
    private LocalDateTime createdAt;
}
