package com.blockattend.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test/secure")
    public String secureEndpoint(Authentication authentication) {
        return "Hello, " + authentication.getName() + "! You accessed a protected endpoint.";
        }
        @GetMapping("/api/admin/test")
public String adminOnly(Authentication authentication) {
    return "Welcome Admin " + authentication.getName();
}

@GetMapping("/api/student/test")
public String studentOnly(Authentication authentication) {
    return "Welcome Student " + authentication.getName();
}
}
