package com.example.carrentalservice.mapper;

import com.example.carrentalservice.dto.UserDto;
import com.example.carrentalservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.id", target = "roleId")
    UserDto toDto(User user);

    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto userDto);
}