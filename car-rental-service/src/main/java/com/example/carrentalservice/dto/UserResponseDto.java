package com.example.carrentalservice.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String role;
}