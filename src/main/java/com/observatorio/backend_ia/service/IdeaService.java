package com.observatorio.backend_ia.service;

import com.observatorio.backend_ia.model.Idea;
import com.observatorio.backend_ia.model.enums.IdeaStatus;
import com.observatorio.backend_ia.repository.IdeaRepository;
import com.observatorio.backend_ia.service.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdeaService {

    private final IdeaRepository ideaRepository;
    private final EmailService emailService;

    @Value("${app.default.recipient}")
    private String defaultRecipient;

    public Idea sendIdea(String name, String idea, String ethicalConcern) {
        Idea entity = new Idea();
        entity.setName(name);
        entity.setIdea(idea);
        entity.setEthicalConcern(ethicalConcern);
        entity.setStatus(IdeaStatus.PENDIENTE_REVISION);

        entity = ideaRepository.save(entity);

        try {
            emailService.sendIdeaEmail(defaultRecipient, name, idea, ethicalConcern);
        } catch (MessagingException e) {
            log.error("Error al enviar el correo de la idea", e);
        }

        return entity;
    }

    public Page<Idea> getApprovedIdeas(Pageable pageable) {
        return ideaRepository.findByStatus(IdeaStatus.APROBADO, pageable);
    }

    public Page<Idea> getAllIdeas(Pageable pageable) {
        return ideaRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public Idea updateStatus(Long id, IdeaStatus newStatus) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Idea no encontrada con id: " + id));

        IdeaStatus current = idea.getStatus();

        if (!isValidTransition(current, newStatus)) {
            throw new IllegalStateException(
                    "No se puede cambiar el estado de " + current + " a " + newStatus
            );
        }

        idea.setStatus(newStatus);
        idea.setUpdatedAt(LocalDateTime.now());
        return ideaRepository.save(idea);
    }

    private boolean isValidTransition(IdeaStatus current, IdeaStatus newStatus) {
        return switch (current) {
            case PENDIENTE_REVISION -> newStatus == IdeaStatus.APROBADO || newStatus == IdeaStatus.RECHAZADO;
            case APROBADO -> newStatus == IdeaStatus.HECHO;
            case RECHAZADO, HECHO -> false;
        };
    }
}
