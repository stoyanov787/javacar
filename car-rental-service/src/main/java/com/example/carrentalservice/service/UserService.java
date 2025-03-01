package com.example.carrentalservice.service;

import com.example.carrentalservice.dto.UserDto;
import com.example.carrentalservice.entity.Role;
import com.example.carrentalservice.entity.User;
import com.example.carrentalservice.exception.ResourceNotFoundException;
import com.example.carrentalservice.mapper.UserMapper;
import com.example.carrentalservice.repository.RoleRepository;
import com.example.carrentalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + userDto.getRoleId()));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toDto(user);
    }
}