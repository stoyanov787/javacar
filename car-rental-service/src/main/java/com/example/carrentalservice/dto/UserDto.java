package com.example.carrentalservice.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Long roleId;
}