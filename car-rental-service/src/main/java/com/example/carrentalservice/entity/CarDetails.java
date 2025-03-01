package com.example.carrentalservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car_details")
@Getter
@Setter
@NoArgsConstructor
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String description;

    @Column(length = 20)
    private String color;

    private Integer seatingCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @OneToOne(mappedBy = "carDetails")
    private Car car;
}