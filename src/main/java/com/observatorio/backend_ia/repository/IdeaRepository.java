package com.observatorio.backend_ia.repository;

import com.observatorio.backend_ia.model.Idea;
import com.observatorio.backend_ia.model.enums.IdeaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, Long> {

    Page<Idea> findByStatus(IdeaStatus status, Pageable pageable);

    Page<Idea> findByStatusOrderByCreatedAtDesc(IdeaStatus status, Pageable pageable);

    Page<Idea> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
