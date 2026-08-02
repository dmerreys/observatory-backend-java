package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.commons.api.GenericResponse;
import com.observatorio.backend_ia.model.Idea;
import com.observatorio.backend_ia.model.enums.IdeaStatus;
import com.observatorio.backend_ia.service.IdeaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    @PostMapping
    public ResponseEntity<GenericResponse<Idea>> sendIdea(@Valid @RequestBody IdeaRequest request) {
        try {
            Idea idea = ideaService.sendIdea(request.getName(), request.getIdea(), request.getEthicalConcern());
            return ResponseEntity.ok(GenericResponse.createSuccessResponse(idea, java.util.List.of("Idea enviada correctamente")));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.createErrorResponse("Error al enviar la idea: " + e.getMessage()));
        }
    }

    @GetMapping("/approved")
    public ResponseEntity<GenericResponse<Page<Idea>>> getApprovedIdeas(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Idea> ideas = ideaService.getApprovedIdeas(pageable);
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(ideas));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<Page<Idea>>> getAllIdeas(
            @RequestParam(required = false) IdeaStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Idea> ideas = ideaService.getAllIdeas(pageable, status);
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(ideas));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<Idea>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIdeaStatusRequest request) {
        try {
            Idea idea = ideaService.updateStatus(id, request.getStatus());
            return ResponseEntity.ok(GenericResponse.createSuccessResponse(idea, java.util.List.of("Estado actualizado correctamente")));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(GenericResponse.createErrorResponse(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(GenericResponse.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.createErrorResponse("Error al actualizar el estado: " + e.getMessage()));
        }
    }
}
