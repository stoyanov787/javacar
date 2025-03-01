package com.example.carrentalservice.repository;

import com.example.carrentalservice.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    boolean existsByName(String name);
}