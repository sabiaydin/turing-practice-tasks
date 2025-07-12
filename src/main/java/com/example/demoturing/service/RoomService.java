package com.example.demoturing.service;

import com.example.demoturing.dao.entity.Hotel;
import com.example.demoturing.dao.entity.Room;
import com.example.demoturing.dao.repository.HotelRepository;
import com.example.demoturing.dao.repository.RoomRepository;
import com.example.demoturing.exception.*;
import com.example.demoturing.mapper.RoomMapper;
import com.example.demoturing.model.request.RoomRequest;
import com.example.demoturing.model.response.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public List<RoomResponse> getRoomsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND));

        List<Room> rooms = roomRepository.findByHotel(hotel);
        return roomMapper.toResponseList(rooms);
    }

    public RoomResponse createRoomForHotel(Long hotelId, RoomRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND));

        Room room = roomMapper.toEntity(request);
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponse(savedRoom);
    }

    public void deleteAllRoomsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND));

        List<Room> rooms = roomRepository.findByHotel(hotel);
        roomRepository.deleteAll(rooms);
    }

    public RoomResponse updateRoomOfHotel(Long hotelId, Long roomId, RoomRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ErrorCode.ROOM_NOT_FOUND, ErrorMessage.ROOM_NOT_FOUND));

        if (!room.getHotel().getId().equals(hotelId)) {
            throw new RoomNotFoundException(ErrorCode.ROOM_NOT_FOUND, "Room does not belong to specified hotel");
        }

        room.setRoomNumber(request.getRoomNumber());
        room.setPrice(request.getPrice());
        room.setRoomStatus(request.getRoomStatus());

        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toResponse(updatedRoom);
    }
}
