package com.example.demoturing.service;

import com.example.demoturing.dao.entity.Room;
import com.example.demoturing.exception.HotelNotFound;
import com.example.demoturing.exception.RoomNotFound;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {
    private final Map<Long, Room> roomMap = new HashMap<>();

    public List<Room> getAll() {
        return new ArrayList<>(roomMap.values());
    }

    public Room getById(Long id) {
        if (!roomMap.containsKey(id)) {
            throw new RoomNotFound("Hotel with id " + id + " does not exist.");
        }
        return roomMap.get(id);
    }

    public Room create(Room room) {
        roomMap.put(room.getId(), room);
        return room;
    }

    public Room update(Long id, Room room) {
        if (!roomMap.containsKey(id)) {
            throw new RoomNotFound("Room with ID " + id + " not found");
        }
        room.setId(id);
        roomMap.put(id, room);
        return room;
    }


    public void deleteById(Long id) {
        if (roomMap.remove(id) == null) {
            throw new HotelNotFound("Hotel with id " + id + " does not exist.");
        }
    }

}
