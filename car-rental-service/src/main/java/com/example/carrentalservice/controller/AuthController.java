package com.example.carrentalservice.controller;

import com.example.carrentalservice.dto.LoginRequest;
import com.example.carrentalservice.dto.LoginResponse;
import com.example.carrentalservice.dto.UserDto;
import com.example.carrentalservice.service.AuthService;
import com.example.carrentalservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "APIs for authentication operations")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Operation(
            summary = "Register new user",
            description = "Register a new user with the provided details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User registered successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}