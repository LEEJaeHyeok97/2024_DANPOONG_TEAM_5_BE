package com.jangburich.domain.user.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping
    public String mainAPI() {
        return "Hello World!";
    }
}
