package com.walking.tree.parking.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @GetMapping("/auth/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return Map.of("authenticated", false);
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("authenticated", true);
        userInfo.put("email", jwt.getClaimAsString("email"));
        userInfo.put("name", jwt.getClaimAsString("name"));
        userInfo.put("picture", jwt.getClaimAsString("picture"));
        userInfo.put("authorities", jwt.getClaimAsStringList("roles")); // if you map roles later

        return userInfo;
    }
}
