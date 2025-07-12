package com.example.demoturing.service;

import com.example.demoturing.entity.Hotel;
import com.example.demoturing.exception.*;
import com.example.demoturing.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository repository;
    public List<Hotel> getAll() {
        return repository.findAll();
    }

    public Hotel getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND));
    }

    public Hotel create(Hotel hotel) {
        return repository.save(hotel);
    }

    public Hotel update(Long id, Hotel hotel) {
        if (!repository.existsById(id)) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        hotel.setId(id);
        return repository.save(hotel);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public void uploadHotelsFromExcel(MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xlsx")) {
            throw new InvalidFileFormatException(ErrorCode.INVALID_FILE_FORMAT, ErrorMessage.INVALID_FILE_FORMAT);
        }

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            List<Hotel> hotels = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                Hotel hotel = new Hotel();
                hotel.setId((long) row.getCell(0).getNumericCellValue());
                hotel.setName(row.getCell(1).getStringCellValue());
                hotel.setLocation(row.getCell(2).getStringCellValue());
                hotel.setCreatedAt(LocalDateTime.parse(row.getCell(3).getStringCellValue(), formatter));

                hotels.add(hotel);
            }

            repository.saveAll(hotels);
        } catch (IOException e) {
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, ErrorMessage.FILE_PROCESSING_ERROR);
        } catch (Exception e) {
            throw new InvalidExcelDataException(ErrorCode.INVALID_EXCEL_DATA, ErrorMessage.INVALID_EXCEL_DATA);
        }
    }
}