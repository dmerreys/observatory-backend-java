package com.observatorio.backend_ia.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = true)
    private Set<String> roles = new HashSet<>();

    // Temporal: solo hasta que Lombok funcione 100% (puedes borrarlos después)
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public Set<String> getRoles() { return roles; }
}