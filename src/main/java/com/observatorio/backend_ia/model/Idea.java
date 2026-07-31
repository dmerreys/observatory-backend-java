package com.observatorio.backend_ia.model;

import com.observatorio.backend_ia.model.enums.IdeaStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ideas")
@Data
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String idea;

    @Column(columnDefinition = "TEXT")
    private String ethicalConcern;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IdeaStatus status = IdeaStatus.PENDIENTE_REVISION;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}
