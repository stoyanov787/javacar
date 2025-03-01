package com.example.carrentalservice.controller;

import com.example.carrentalservice.dto.ServiceDto;
import com.example.carrentalservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto) {
        return ResponseEntity.ok(serviceService.createService(serviceDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDto> updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{serviceId}/cars/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDto> addCarToService(
            @PathVariable Long serviceId,
            @PathVariable Long carId) {
        return ResponseEntity.ok(serviceService.addCarToService(serviceId, carId));
    }

    @DeleteMapping("/{serviceId}/cars/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceDto> removeCarFromService(
            @PathVariable Long serviceId,
            @PathVariable Long carId) {
        return ResponseEntity.ok(serviceService.removeCarFromService(serviceId, carId));
    }
}