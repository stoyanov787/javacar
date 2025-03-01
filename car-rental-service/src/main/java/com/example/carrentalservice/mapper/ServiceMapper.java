package com.example.carrentalservice.mapper;

import com.example.carrentalservice.dto.ServiceDto;
import com.example.carrentalservice.entity.Car;
import com.example.carrentalservice.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @Mapping(target = "carIds", expression = "java(getCarIds(service))")
    ServiceDto toDto(Service service);

    @Mapping(target = "cars", ignore = true)
    Service toEntity(ServiceDto serviceDto);

    default Set<Long> getCarIds(Service service) {
        if (service.getCars() == null) {
            return null;
        }
        return service.getCars().stream()
                .map(Car::getId)
                .collect(Collectors.toSet());
    }
}