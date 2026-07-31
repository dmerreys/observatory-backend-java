package com.observatorio.backend_ia.service;

import com.observatorio.backend_ia.dto.TokenValidationResponse;
import com.observatorio.backend_ia.repository.UserRepository;
import com.observatorio.backend_ia.security.JwtUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public TokenValidationResponse validateToken(String token) {
        String username = jwtUtil.extractUsername(token);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        return new TokenValidationResponse(user.getUsername(), new ArrayList<>(user.getRoles()));
    }
}
