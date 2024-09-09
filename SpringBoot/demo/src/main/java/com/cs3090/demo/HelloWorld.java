package com.cs3090.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloWorld {

    // Home endpoint, returns a dynamic message
    @RequestMapping("/")
    public String home(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "! Welcome to Spring Boot!";
    }

    // Status endpoint to check the health of the application
    @RequestMapping("/status")
    public String status() {
        return "Application is running smoothly!";
    }

    public static void main(String[] args) {
        // Run the Spring Boot application
        SpringApplication.run(HelloWorld.class, args);
    }
}