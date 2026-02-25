package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> home() {
        return ResponseEntity.ok(helloService.getWelcomeMessage());
    }

    @GetMapping("/hellothere")
    public ResponseEntity<ApiResponse> hello() {
        return ResponseEntity.ok(helloService.getWelcomeMessage());
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(helloService.getHealthStatus());
    }

    @GetMapping("/greet/{name}")
    public ResponseEntity<ApiResponse> greet(@PathVariable String name) {
        return ResponseEntity.ok(new ApiResponse(
            "Hello, " + name + "! Welcome to Jenkins CI/CD.",
            "running",
            "1.0.0",
            System.currentTimeMillis()
        ));
    }
    @GetMapping("/greet/{name}/number/{number}")
    public ResponseEntity<ApiResponse> greetNumber(@PathVariable String name,@PathVariable String number) {
        return ResponseEntity.ok(new ApiResponse(
            "Hello, " + name + "! Welcome to Jenkins CI/CD. with number " + number,
            "running",
            "1.0.0",
            System.currentTimeMillis()
        ));
    }






}
