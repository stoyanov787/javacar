package com.example.carrentalservice.service;

import com.example.carrentalservice.dto.RentalDto;
import com.example.carrentalservice.entity.Car;
import com.example.carrentalservice.entity.Rental;
import com.example.carrentalservice.entity.User;
import com.example.carrentalservice.mapper.RentalMapper;
import com.example.carrentalservice.repository.CarRepository;
import com.example.carrentalservice.repository.RentalRepository;
import com.example.carrentalservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RentalMapper rentalMapper;

    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<RentalDto> getRentalsByUserId(Long userId) {
        return rentalRepository.findByUserId(userId).stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }

    public RentalDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + id));
        return rentalMapper.toDto(rental);
    }

    @Transactional
    public RentalDto createRental(RentalDto rentalDto) {
        User user = userRepository.findById(rentalDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Car car = carRepository.findById(rentalDto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Rental rental = rentalMapper.toEntity(rentalDto);
        rental.setUser(user);
        rental.setCar(car);

        Rental savedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(savedRental);
    }

    @Transactional
    public RentalDto updateRental(Long id, RentalDto rentalDto) {
        if (!rentalRepository.existsById(id)) {
            throw new EntityNotFoundException("Rental not found with id: " + id);
        }

        User user = userRepository.findById(rentalDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Car car = carRepository.findById(rentalDto.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        Rental rental = rentalMapper.toEntity(rentalDto);
        rental.setId(id);
        rental.setUser(user);
        rental.setCar(car);

        Rental updatedRental = rentalRepository.save(rental);
        return rentalMapper.toDto(updatedRental);
    }

    @Transactional
    public void deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new EntityNotFoundException("Rental not found with id: " + id);
        }
        rentalRepository.deleteById(id);
    }
}