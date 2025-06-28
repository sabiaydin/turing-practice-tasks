package com.example.demoturing.service;

import com.example.demoturing.entity.Hotel;
import exception.HotelNotFound;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {
    private final Map<Long, Hotel> hotelMap = new HashMap<>();

    public List<Hotel> getAll() {
        return new ArrayList<>(hotelMap.values());
    }

    public Hotel getById(Long id) {
        return hotelMap.get(id);
    }

    public Hotel create(Hotel hotel) {
        hotelMap.put(hotel.getId(), hotel);
        return hotel;
    }

    public Hotel update(Long id, Hotel hotel) {
        if (hotelMap.containsKey(id)) {
            hotel.setId(id);
            hotelMap.put(id, hotel);
            return hotel;
        }
        return null;

    }

    public void deleteById(Long id) {
        if (hotelMap.remove(id) == null) {
            throw new HotelNotFound("Hotel with id " + id + " does not exist.");
        }
    }
}