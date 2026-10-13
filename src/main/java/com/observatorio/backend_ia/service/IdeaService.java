package com.observatorio.backend_ia.service;

import com.observatorio.backend_ia.model.Idea;
import com.observatorio.backend_ia.model.enums.IdeaCategory;
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

    public Idea sendIdea(String name, String idea, IdeaCategory category, String ethicalConcern) {
        if (category == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        String sanitizedEthicalConcern = sanitizeEthicalConcern(category, ethicalConcern);

        Idea entity = new Idea();
        entity.setName(name);
        entity.setIdea(idea);
        entity.setCategory(category);
        entity.setEthicalConcern(sanitizedEthicalConcern);
        entity.setStatus(IdeaStatus.PENDIENTE_REVISION);

        entity = ideaRepository.save(entity);

        try {
            emailService.sendIdeaEmail(defaultRecipient, name, idea, category, sanitizedEthicalConcern);
        } catch (MessagingException e) {
            log.error("Error al enviar el correo de la idea", e);
        }

        return entity;
    }

    public Page<Idea> getApprovedIdeas(Pageable pageable, IdeaCategory category) {
        if (category == null) {
            return ideaRepository.findByStatus(IdeaStatus.APROBADO, pageable);
        }
        return ideaRepository.findByStatusAndCategory(IdeaStatus.APROBADO, category, pageable);
    }

    public Page<Idea> getAllIdeas(Pageable pageable, IdeaStatus status, IdeaCategory category) {
        if (status != null && category != null) {
            return ideaRepository.findByStatusAndCategoryOrderByCreatedAtDesc(status, category, pageable);
        }
        if (status != null) {
            return ideaRepository.findByStatusOrderByCreatedAtDesc(status, pageable);
        }
        if (category != null) {
            return ideaRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
        }
        return ideaRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional
    public Idea updateStatus(Long id, IdeaStatus newStatus) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Idea no encontrada con id: " + id));

        IdeaStatus current = idea.getStatus();

        if (!isValidTransition(current, newStatus, idea.getCategory())) {
            throw new IllegalStateException(
                    "No se puede cambiar el estado de " + current + " a " + newStatus
                            + " para la categoría " + idea.getCategory()
            );
        }

        idea.setStatus(newStatus);
        idea.setUpdatedAt(LocalDateTime.now());
        return ideaRepository.save(idea);
    }

    private String sanitizeEthicalConcern(IdeaCategory category, String ethicalConcern) {
        boolean hasContent = ethicalConcern != null && !ethicalConcern.isBlank();
        if (!category.allowsEthicalConcern() && hasContent) {
            throw new IllegalArgumentException(
                    "La consideración ética solo aplica a SUGERENCIA"
            );
        }
        return hasContent ? ethicalConcern : null;
    }

    private boolean isValidTransition(IdeaStatus current, IdeaStatus newStatus, IdeaCategory category) {
        return switch (current) {
            case PENDIENTE_REVISION -> newStatus == IdeaStatus.APROBADO || newStatus == IdeaStatus.RECHAZADO;
            case APROBADO -> newStatus == IdeaStatus.HECHO && category.allowsDoneStatus();
            case RECHAZADO, HECHO -> false;
        };
    }
}
