package com.observatorio.backend_ia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contributors")
@Data
public class Contributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 300)
    private String affiliation;  // universidad, cargo, etc.

    @Column(length = 200)
    private String email;
}