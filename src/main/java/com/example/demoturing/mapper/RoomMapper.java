package com.example.demoturing.mapper;

import com.example.demoturing.dao.entity.Room;
import com.example.demoturing.model.request.RoomRequest;
import com.example.demoturing.model.response.RoomResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toEntity(RoomRequest request);

    RoomResponse toResponse(Room room);

    List<RoomResponse> toResponseList(List<Room> rooms);
}

