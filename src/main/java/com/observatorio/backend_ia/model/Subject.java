package com.observatorio.backend_ia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subjects")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;  // "ética", "gobernanza", "innovación", etc.
}