package com.observatorio.backend_ia.service;

import com.observatorio.backend_ia.model.Publication;
import com.observatorio.backend_ia.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    private final PublicationRepository repository;

    public PublicationService(PublicationRepository repository) {
        this.repository = repository;
    }

    public List<Publication> getAll() {
        return repository.findAll();
    }

    public Publication save(Publication publication) {
        // Aquí puedes agregar lógica: buscar o crear subjects/contributors si vienen por nombre
        return repository.save(publication);
    }
}