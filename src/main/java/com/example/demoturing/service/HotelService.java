package com.example.demoturing.service;

import com.example.demoturing.entity.Hotel;
import exception.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Hotel hotel = hotelMap.get(id);
        if (hotel == null) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        return hotel;
    }

    public Hotel create(Hotel hotel) {
        hotelMap.put(hotel.getId(), hotel);
        return hotel;
    }

    public Hotel update(Long id, Hotel hotel) {
        if (!hotelMap.containsKey(id)) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        hotel.setId(id);
        hotelMap.put(id, hotel);
        return hotel;
    }

    public void deleteById(Long id) {
        if (!hotelMap.containsKey(id)) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        hotelMap.remove(id);
    }

    public void uploadHotelsFromExcel(MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xlsx")) {
            throw new InvalidFileFormatException(ErrorCode.INVALID_FILE_FORMAT, ErrorMessage.INVALID_FILE_FORMAT);
        }

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                Hotel hotel = new Hotel();
                hotel.setId((long) row.getCell(0).getNumericCellValue());
                hotel.setName(row.getCell(1).getStringCellValue());
                hotel.setLocation(row.getCell(2).getStringCellValue());
                hotel.setCreatedAt(LocalDateTime.parse(row.getCell(3).getStringCellValue(), formatter));

                hotelMap.put(hotel.getId(), hotel);
            }
        } catch (IOException e) {
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, ErrorMessage.FILE_PROCESSING_ERROR);
        } catch (Exception e) {
            throw new InvalidExcelDataException(ErrorCode.INVALID_EXCEL_DATA, ErrorMessage.INVALID_EXCEL_DATA);
        }
    }
}