package com.example.carrentalservice.repository;

import com.example.carrentalservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    // Add custom queries if needed
}