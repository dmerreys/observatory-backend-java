// controller/ResourceController.java
package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.commons.api.GenericResponse;
import com.observatorio.backend_ia.controller.dto.ResourceRequest;
import com.observatorio.backend_ia.controller.dto.ResourceResponse;
import com.observatorio.backend_ia.controller.dto.TopicCountResponse;
import com.observatorio.backend_ia.model.Resource;
import com.observatorio.backend_ia.model.enums.ResourceTopic;
import com.observatorio.backend_ia.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceRepository resourceRepository;

    @GetMapping
    public ResponseEntity<GenericResponse<List<ResourceResponse>>> getAll() {
        List<Resource> resources = resourceRepository.findAllByOrderByCreatedAtDesc();
        List<ResourceResponse> dtos = resources.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(dtos));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<GenericResponse<List<ResourceResponse>>> getByType(@PathVariable String type) {
        List<Resource> resources = resourceRepository.findByType(type);
        List<ResourceResponse> dtos = resources.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(dtos));
    }

    @GetMapping("/featured")
    public ResponseEntity<GenericResponse<List<ResourceResponse>>> getFeatured() {
        List<Resource> resources = resourceRepository.findByFeaturedTrue();
        List<ResourceResponse> dtos = resources.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(dtos));
    }

    @GetMapping("/count-by-topic")
    public ResponseEntity<GenericResponse<List<TopicCountResponse>>> countByTopic() {
        List<Object[]> results = resourceRepository.countByTopic();
        List<TopicCountResponse> dtos = results.stream()
                .map(row -> new TopicCountResponse(((ResourceTopic) row[0]).name(), (Long) row[1]))
                .collect(Collectors.toList());
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(dtos));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<ResourceResponse>> create(@RequestBody ResourceRequest request) {
        Resource resource = new Resource();
        resource.setTitle(request.getTitle());
        resource.setDescription(request.getDescription());
        resource.setType(request.getType());
        resource.setUrl(request.getUrl());
        resource.setSource(request.getSource());
        resource.setTopic(request.getTopic());
        resource.setFeatured(Boolean.TRUE.equals(request.getFeatured()));

        if (resource.isFeatured() && resource.getTopic() != null) {
            Optional<Resource> existingFeatured = resourceRepository
                    .findByTopicAndFeaturedTrue(resource.getTopic());
            existingFeatured.ifPresent(featured -> {
                featured.setFeatured(false);
                resourceRepository.save(featured);
            });
        }

        Resource saved = resourceRepository.save(resource);
        return ResponseEntity.ok(GenericResponse.createSuccessResponse(toResponse(saved)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<Void>> delete(@PathVariable Long id) {
        if (!resourceRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(GenericResponse.createErrorResponse("Recurso no encontrado"));
        }
        resourceRepository.deleteById(id);
        return ResponseEntity.ok(GenericResponse.createSuccessResponse());
    }

    private ResourceResponse toResponse(Resource r) {
        ResourceResponse dto = new ResourceResponse();
        dto.setId(r.getId());
        dto.setTitle(r.getTitle());
        dto.setDescription(r.getDescription());
        dto.setType(r.getType());
        dto.setUrl(r.getUrl());
        dto.setSource(r.getSource());
        dto.setTopic(r.getTopic());
        dto.setFeatured(r.isFeatured());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }
}