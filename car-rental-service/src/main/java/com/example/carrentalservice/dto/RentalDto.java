package com.example.carrentalservice.dto;

import com.example.carrentalservice.entity.RentalStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalDto {
    private Long id;
    private Long userId;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private RentalStatus rentalStatus;
}