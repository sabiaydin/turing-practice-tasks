package com.example.demoturing.controller;

import com.example.demoturing.dao.entity.Hotel;
import com.example.demoturing.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            return ResponseEntity.ok(hotelService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Hotel> create(@RequestBody @Valid Hotel hotel) {
        return ResponseEntity.status(201).body(hotelService.create(hotel));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Hotel> update(@PathVariable Long id, @RequestBody @Valid Hotel hotel) {
       return ResponseEntity.ok(hotelService.update(id, hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        hotelService.uploadHotelsFromExcel(file);
        return ResponseEntity.ok("Hotels uploaded successfully.");
    }

}
