package com.example.carrentalservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 50)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer mileage;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_details_id")
    private CarDetails carDetails;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarImage> images = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "car_service",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services = new HashSet<>();

    @OneToMany(mappedBy = "car")
    private Set<Rental> rentals = new HashSet<>();
}