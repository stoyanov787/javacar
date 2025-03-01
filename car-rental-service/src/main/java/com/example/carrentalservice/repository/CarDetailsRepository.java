package com.example.carrentalservice.repository;

import com.example.carrentalservice.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
    // Add custom queries if needed
}