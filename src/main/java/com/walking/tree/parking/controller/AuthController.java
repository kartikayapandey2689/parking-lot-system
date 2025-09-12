package com.walking.tree.parking.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        if (authentication == null) return Map.of("authenticated", false);
        return Map.of(
                "name", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }
}
