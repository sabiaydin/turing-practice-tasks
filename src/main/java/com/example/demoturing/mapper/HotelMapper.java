package com.example.demoturing.mapper;

import com.example.demoturing.dao.entity.Hotel;
import com.example.demoturing.model.request.HotelRequest;
import com.example.demoturing.model.response.HotelResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(HotelRequest request);

    HotelResponse toResponse(Hotel hotel);

    List<HotelResponse> toResponseList(List<Hotel> hotels);
}
