package com.observatorio.backend_ia.controller;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}