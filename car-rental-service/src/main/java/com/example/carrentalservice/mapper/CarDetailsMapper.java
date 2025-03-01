package com.example.carrentalservice.mapper;

import com.example.carrentalservice.dto.CarDetailsDto;
import com.example.carrentalservice.entity.CarDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarDetailsMapper {
    CarDetailsDto toDto(CarDetails carDetails);

    @Mapping(target = "car", ignore = true)
    CarDetails toEntity(CarDetailsDto carDetailsDto);
}