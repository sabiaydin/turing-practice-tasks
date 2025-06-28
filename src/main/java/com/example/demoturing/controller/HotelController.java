package com.example.demoturing.controller;

import com.example.demoturing.entity.Hotel;
import com.example.demoturing.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@PathVariable Long id) {
        Hotel hotel = hotelService.getById(id);
            return ResponseEntity.ok(hotel);
    }

    @PostMapping
    public ResponseEntity<Hotel> create(@RequestBody @Valid Hotel hotel) {
        Hotel createdHotel = hotelService.create(hotel);
        return ResponseEntity.status(201).body(createdHotel);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Hotel> update(@PathVariable Long id, @RequestBody @Valid Hotel hotel) {
        Hotel updatedHotel = hotelService.update(id, hotel);
       return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
