package com.example.carrentalservice.service;

import com.example.carrentalservice.dto.LoginRequest;
import com.example.carrentalservice.dto.LoginResponse;
import com.example.carrentalservice.entity.User;
import com.example.carrentalservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Try to authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Fetch the user
            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + loginRequest.getUsername()));

            // Return user info
            return new LoginResponse(
                    "session-token", // Not actually using token, just a placeholder
                    user.getUsername(),
                    user.getRole().getName()
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password", e);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}