package com.observatorio.backend_ia.controller;  // o crea subpaquete dto

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
}