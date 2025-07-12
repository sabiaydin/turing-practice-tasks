package com.example.demoturing.model.request;

import com.example.demoturing.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequest {
    @NotNull
    private Integer roomNumber;

    @NotNull
    private BigDecimal price;

    @NotNull
    private RoomStatus roomStatus;
}
