package com.example.demoturing.service;

import com.example.demoturing.dao.entity.Hotel;
import com.example.demoturing.exception.*;
import com.example.demoturing.dao.repository.HotelRepository;
import com.example.demoturing.mapper.HotelMapper;
import com.example.demoturing.model.request.HotelRequest;
import com.example.demoturing.model.response.HotelResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    public List<HotelResponse> getAll() {
        log.info("Fetching all hotels");
        List<Hotel> hotels = repository.findAll();
        return mapper.toResponseList(hotels);
    }

    public HotelResponse getById(Long id) {
        log.info("Fetching hotel by id: {}", id);
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Hotel not found with id: {}", id);
                    return new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
                });
        return mapper.toResponse(hotel);
    }

    public HotelResponse create(HotelRequest request) {
        log.info("Creating hotel with name: {}", request.getName());
        Hotel hotel = mapper.toEntity(request);
        return mapper.toResponse(repository.save(hotel));
    }

    public HotelResponse update(Long id, HotelRequest request) {
        log.info("Updating hotel with id: {}", id);
        if (!repository.existsById(id)) {
            log.warn("Cannot update, hotel not found with id: {}", id);
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        Hotel hotel = mapper.toEntity(request);
        hotel.setId(id);
        return mapper.toResponse(repository.save(hotel));
    }

    public void deleteById(Long id) {
        log.info("Deleting hotel with id: {}", id);
        if (!repository.existsById(id)) {
            log.warn("Cannot delete, hotel not found with id: {}", id);
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessage.HOTEL_NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public void uploadHotelsFromExcel(MultipartFile file) {
        log.info("Uploading hotels from Excel file: {}", file.getOriginalFilename());
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".xlsx")) {
            log.error("Invalid file format: {}", file.getOriginalFilename());
            throw new InvalidFileFormatException(ErrorCode.INVALID_FILE_FORMAT, ErrorMessage.INVALID_FILE_FORMAT);
        }

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            List<Hotel> hotels = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Hotel hotel = new Hotel();
                hotel.setId((long) row.getCell(0).getNumericCellValue());
                hotel.setName(row.getCell(1).getStringCellValue());
                hotel.setLocation(row.getCell(2).getStringCellValue());
                hotel.setCreatedAt(LocalDateTime.parse(row.getCell(3).getStringCellValue(), formatter));

                hotels.add(hotel);
            }

            repository.saveAll(hotels);
            log.info("Successfully uploaded {} hotels from Excel", hotels.size());

        } catch (IOException e) {
            log.error("File processing error", e);
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, ErrorMessage.FILE_PROCESSING_ERROR);
        } catch (Exception e) {
            log.error("Invalid Excel data", e);
            throw new InvalidExcelDataException(ErrorCode.INVALID_EXCEL_DATA, ErrorMessage.INVALID_EXCEL_DATA);
        }
    }
}
