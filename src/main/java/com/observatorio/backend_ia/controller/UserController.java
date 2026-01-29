package com.observatorio.backend_ia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("No autenticado");
        }

        Map<String, Object> info = new HashMap<>();
        info.put("username", userDetails.getUsername());
        info.put("roles", userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(info);
    }
}