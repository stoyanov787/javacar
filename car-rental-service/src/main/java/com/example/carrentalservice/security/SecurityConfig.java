package com.example.carrentalservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Allow all static resources
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/*.html").permitAll()
                        .requestMatchers("/", "/index.html", "/login.html", "/register.html").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/*.ico").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                        .requestMatchers("/api/cars/**", "/cars.html").permitAll()
                        // For any other request, require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}