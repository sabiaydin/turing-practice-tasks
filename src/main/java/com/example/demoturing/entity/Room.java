package com.example.demoturing.entity;

import com.example.demoturing.enums.RoomStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private Long id;

    @NotNull(message = "Hotel must not be null")
    private Hotel hotel;

    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be at least 1")
    private Integer roomNumber;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Room status is required")
    private RoomStatus roomStatus;
}
