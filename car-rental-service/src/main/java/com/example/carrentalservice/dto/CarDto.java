package com.example.carrentalservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private CarDetailsDto carDetails;
}