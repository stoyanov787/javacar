package com.example.carrentalservice.controller;

import com.example.carrentalservice.dto.CarDto;
import com.example.carrentalservice.service.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Car Management", description = "APIs for car operations")
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDto> createCar(
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("year") Integer year,
            @RequestParam("price") BigDecimal price,
            @RequestParam("mileage") Integer mileage,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {

        CarDto carDto = new CarDto();
        carDto.setBrand(brand);
        carDto.setModel(model);
        carDto.setYear(year);
        carDto.setPrice(price);
        carDto.setMileage(mileage);

        return ResponseEntity.ok(carService.createCar(carDto, images));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDto> updateCar(
            @PathVariable Long id,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("year") Integer year,
            @RequestParam("price") BigDecimal price,
            @RequestParam("mileage") Integer mileage,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {

        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setBrand(brand);
        carDto.setModel(model);
        carDto.setYear(year);
        carDto.setPrice(price);
        carDto.setMileage(mileage);

        return ResponseEntity.ok(carService.updateCar(id, carDto, images));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCarImage(@PathVariable Long id) {
        byte[] image = carService.getCarImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}