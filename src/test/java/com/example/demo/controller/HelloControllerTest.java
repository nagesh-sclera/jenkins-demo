package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloService helloService;

    @Test
    void testHelloEndpoint() throws Exception {
        when(helloService.getWelcomeMessage()).thenReturn(
            new ApiResponse("Hello from Spring Boot + Jenkins CI/CD!", "running", "1.0.0", 123L)
        );

        mockMvc.perform(get("/api/hello"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("running"))
            .andExpect(jsonPath("$.message").value("Hello from Spring Boot + Jenkins CI/CD!"));
    }

    @Test
    void testHealthEndpoint() throws Exception {
        when(helloService.getHealthStatus()).thenReturn(
            new ApiResponse("Application is healthy", "healthy", "1.0.0", 123L)
        );

        mockMvc.perform(get("/api/healthcheck"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("healthy"));
    }

    @Test
    void testGreetEndpoint() throws Exception {
        mockMvc.perform(get("/api/greet/John"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Hello, John! Welcome to Jenkins CI/CD."));
    }
}
