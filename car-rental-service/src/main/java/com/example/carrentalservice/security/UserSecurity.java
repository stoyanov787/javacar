package com.example.carrentalservice.security;

import com.example.carrentalservice.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final RentalRepository rentalRepository;

    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return currentUsername.equals(userId.toString());
    }

    public boolean isRentalOwner(Long rentalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return rentalRepository.findById(rentalId)
                .map(rental -> rental.getUser().getUsername().equals(currentUsername))
                .orElse(false);
    }
}