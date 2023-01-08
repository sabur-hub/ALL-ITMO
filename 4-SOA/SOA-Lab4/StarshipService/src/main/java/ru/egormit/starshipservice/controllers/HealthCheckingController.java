package ru.egormit.starshipservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckingController {

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok().build();
    }

}