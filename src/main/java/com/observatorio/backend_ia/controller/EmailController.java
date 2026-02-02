package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.commons.api.GenericResponse;
import com.observatorio.backend_ia.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @Value("${app.default.recipient}")
    private String defaultRecipient;

    @PostMapping
    public ResponseEntity<GenericResponse<String>> sendEmail(@Valid @RequestBody EmailRequest request) {
        try {
            emailService.sendIdeaEmail(defaultRecipient, request.getName(), request.getIdea(), request.getEthicalConcern());
            return ResponseEntity.ok(GenericResponse.createSuccessResponse("Email enviado"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.createErrorResponse("Error al enviar el correo: " + e.getMessage()));
        }
    }
}
