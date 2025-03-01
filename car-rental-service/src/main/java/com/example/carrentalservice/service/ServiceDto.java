package com.example.carrentalservice.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ServiceDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Set<Long> carIds;
}