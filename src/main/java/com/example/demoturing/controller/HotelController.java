package com.example.demoturing.controller;

import com.example.demoturing.model.request.HotelRequest;
import com.example.demoturing.model.response.HotelResponse;
import com.example.demoturing.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<HotelResponse>> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getById(id));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid HotelRequest hotelRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotelRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @RequestBody @Valid HotelRequest hotelRequest) {
        return ResponseEntity.ok(hotelService.update(id, hotelRequest));
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
