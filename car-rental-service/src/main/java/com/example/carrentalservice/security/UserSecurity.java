package com.example.carrentalservice.security;

import com.example.carrentalservice.repository.RentalRepository;
import com.example.carrentalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find user by ID, then check if username matches
        return userRepository.findById(userId)
                .map(user -> user.getUsername().equals(currentUsername))
                .orElse(false);
    }

    public boolean isRentalOwner(Long rentalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return rentalRepository.findById(rentalId)
                .map(rental -> rental.getUser().getUsername().equals(currentUsername))
                .orElse(false);
    }
}