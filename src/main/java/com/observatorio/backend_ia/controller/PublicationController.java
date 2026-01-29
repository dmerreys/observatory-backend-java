package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.client.PythonDataClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private final PythonDataClient pythonClient;

    public PublicationController(PythonDataClient pythonClient) {
        this.pythonClient = pythonClient;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getAll() {
        return pythonClient.getAllPublications();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return pythonClient.getPublicationById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody String publicationJson) {
        return pythonClient.createPublication(publicationJson);
    }

    @PostMapping("/trigger-scrape")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> triggerScrape() {
        return pythonClient.triggerScrape("observatorio", "uce");  // ajusta nombres
    }
}