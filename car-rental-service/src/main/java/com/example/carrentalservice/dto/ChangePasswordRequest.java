package com.example.carrentalservice.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}