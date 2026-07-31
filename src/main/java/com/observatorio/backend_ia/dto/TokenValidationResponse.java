package com.observatorio.backend_ia.dto;

import java.util.List;

public class TokenValidationResponse {

    private String sub;
    private List<String> roles;

    public TokenValidationResponse() {
    }

    public TokenValidationResponse(String sub, List<String> roles) {
        this.sub = sub;
        this.roles = roles;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
