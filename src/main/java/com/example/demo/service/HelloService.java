package com.example.demo.service;

import com.example.demo.model.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public ApiResponse getWelcomeMessage() {
        return new ApiResponse(
            "Hello from Spring Boot + Jenkins CI/CD!",
            "running",
            "1.0.0",
            System.currentTimeMillis()
        );
    }

    public ApiResponse getHealthStatus() {
        return new ApiResponse(
            "Application is healthy",
            "healthy",
            "1.0.0",
            System.currentTimeMillis()
        );
    }
}
