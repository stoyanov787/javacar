package com.example.carrentalservice.mapper;

import com.example.carrentalservice.dto.CarDto;
import com.example.carrentalservice.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarDetailsMapper.class})
public interface CarMapper {
    CarDto toDto(Car car);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    Car toEntity(CarDto carDto);
}