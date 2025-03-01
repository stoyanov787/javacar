package com.example.carrentalservice.mapper;

import com.example.carrentalservice.dto.RentalDto;
import com.example.carrentalservice.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "car.id", target = "carId")
    RentalDto toDto(Rental rental);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "car", ignore = true)
    Rental toEntity(RentalDto rentalDto);
}