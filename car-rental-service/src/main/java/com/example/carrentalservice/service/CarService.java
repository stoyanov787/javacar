package com.example.carrentalservice.service;

import com.example.carrentalservice.dto.CarDto;
import com.example.carrentalservice.entity.Car;
import com.example.carrentalservice.entity.CarImage;
import com.example.carrentalservice.exception.ResourceNotFoundException;
import com.example.carrentalservice.mapper.CarMapper;
import com.example.carrentalservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    public CarDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return carMapper.toDto(car);
    }

    @Transactional
    public CarDto createCar(CarDto carDto, List<MultipartFile> images) {
        Car car = carMapper.toEntity(carDto);

        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    try {
                        CarImage carImage = new CarImage();
                        carImage.setFileName(image.getOriginalFilename());
                        carImage.setFileType(image.getContentType());
                        carImage.setFileContent(image.getBytes());
                        carImage.setCar(car);
                        car.getImages().add(carImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to process image upload", e);
                    }
                }
            }
        }

        Car savedCar = carRepository.save(car);
        return carMapper.toDto(savedCar);
    }

    @Transactional
    public CarDto updateCar(Long id, CarDto carDto, List<MultipartFile> images) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        // Update basic information
        existingCar.setBrand(carDto.getBrand());
        existingCar.setModel(carDto.getModel());
        existingCar.setYear(carDto.getYear());
        existingCar.setPrice(carDto.getPrice());
        existingCar.setMileage(carDto.getMileage());

        // Handle new images
        if (images != null && !images.isEmpty()) {
            // Remove existing images if new ones are provided
            existingCar.getImages().clear();

            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    try {
                        CarImage carImage = new CarImage();
                        carImage.setFileName(image.getOriginalFilename());
                        carImage.setFileType(image.getContentType());
                        carImage.setFileContent(image.getBytes());
                        carImage.setCar(existingCar);
                        existingCar.getImages().add(carImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to process image upload", e);
                    }
                }
            }
        }

        Car updatedCar = carRepository.save(existingCar);
        return carMapper.toDto(updatedCar);
    }

    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    public byte[] getCarImage(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        if (car.getImages() == null || car.getImages().isEmpty()) {
            throw new ResourceNotFoundException("No image found for car with id: " + id);
        }

        // Return the first image
        return car.getImages().iterator().next().getFileContent();
    }
}