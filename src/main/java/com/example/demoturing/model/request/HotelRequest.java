package com.example.demoturing.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelRequest {
    @NotBlank(message = "Hotel name must not be blank")
    @Size(max = 100, message = "Hotel name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Location must not be blank")
    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    @PastOrPresent(message = "CreatedAt must be in the past or present")
    private LocalDateTime createdAt;
}
