package com.example.carrentalservice.dto;

import com.example.carrentalservice.entity.FuelType;
import lombok.Data;

@Data
public class CarDetailsDto {
    private Long id;
    private String description;
    private String color;
    private Integer seatingCapacity;
    private FuelType fuelType;
}