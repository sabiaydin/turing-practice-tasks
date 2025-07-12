package com.example.demoturing.dao.repository;

import com.example.demoturing.dao.entity.Hotel;
import com.example.demoturing.dao.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel(Hotel hotel);
}
