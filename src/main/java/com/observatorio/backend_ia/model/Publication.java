package com.observatorio.backend_ia.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publications")
@Data
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(length = 1000, unique = true)  // enlace único para evitar duplicados
    private String url;

    @Column(length = 200)
    private String source;  // universidad, sitio, etc.

    @Column
    private LocalDateTime scrapeDate = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
        name = "publication_subjects",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "contributors_publication",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "contributor_id")
    )
    private Set<Contributor> contributors = new HashSet<>();

    @Column(nullable = false)
    private boolean excluded = false;  // si está en excluded_publication, marcamos true
}