package com.example.carrentalservice.service;

import com.example.carrentalservice.dto.ServiceDto;
import com.example.carrentalservice.entity.Car;
import com.example.carrentalservice.entity.Service;
import com.example.carrentalservice.mapper.ServiceMapper;
import com.example.carrentalservice.repository.CarRepository;
import com.example.carrentalservice.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final CarRepository carRepository;
    private final ServiceMapper serviceMapper;

    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toDto)
                .collect(Collectors.toList());
    }

    public ServiceDto getServiceById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + id));
        return serviceMapper.toDto(service);
    }

    @Transactional
    public ServiceDto createService(ServiceDto serviceDto) {
        if (serviceRepository.existsByName(serviceDto.getName())) {
            throw new RuntimeException("Service with this name already exists");
        }

        Service service = serviceMapper.toEntity(serviceDto);
        if (serviceDto.getCarIds() != null && !serviceDto.getCarIds().isEmpty()) {
            Set<Car> cars = serviceDto.getCarIds().stream()
                    .map(carId -> carRepository.findById(carId)
                            .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId)))
                    .collect(Collectors.toSet());
            service.setCars(cars);
        }

        Service savedService = serviceRepository.save(service);
        return serviceMapper.toDto(savedService);
    }

    @Transactional
    public ServiceDto updateService(Long id, ServiceDto serviceDto) {
        Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + id));

        Service service = serviceMapper.toEntity(serviceDto);
        service.setId(id);

        if (serviceDto.getCarIds() != null) {
            Set<Car> cars = serviceDto.getCarIds().stream()
                    .map(carId -> carRepository.findById(carId)
                            .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId)))
                    .collect(Collectors.toSet());
            service.setCars(cars);
        } else {
            service.setCars(new HashSet<>());
        }

        Service updatedService = serviceRepository.save(service);
        return serviceMapper.toDto(updatedService);
    }

    @Transactional
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new EntityNotFoundException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }

    @Transactional
    public ServiceDto addCarToService(Long serviceId, Long carId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + serviceId));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));

        service.getCars().add(car);
        Service updatedService = serviceRepository.save(service);
        return serviceMapper.toDto(updatedService);
    }

    @Transactional
    public ServiceDto removeCarFromService(Long serviceId, Long carId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + serviceId));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));

        service.getCars().remove(car);
        Service updatedService = serviceRepository.save(service);
        return serviceMapper.toDto(updatedService);
    }
}