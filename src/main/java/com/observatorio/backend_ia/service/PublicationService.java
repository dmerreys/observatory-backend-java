package com.observatorio.backend_ia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.observatorio.backend_ia.client.create_publication.CreatePublicationResponse;
import com.observatorio.backend_ia.client.load_publication.LoadPublicationRequest;
import com.observatorio.backend_ia.client.load_publication.LoadPublicationsResponse;
import com.observatorio.backend_ia.client.PythonDataClient;
import com.observatorio.backend_ia.client.create_publication.PublicationRequest;
import com.observatorio.backend_ia.commons.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.observatorio.backend_ia.commons.result.Error;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PythonDataClient pythonDataClient;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public Result<CreatePublicationResponse> save(String payloadJson, MultipartFile file) {
        PublicationRequest article = parseJson(payloadJson);

        Set<ConstraintViolation<PublicationRequest>> violations = validator.validate(article);
        if (!violations.isEmpty()) {
            String messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            return Result.failure(Error.failure("Validation", messages));
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        if (!Objects.equals(file.getContentType(), "application/pdf")) {
            throw new IllegalArgumentException("Debe proporcionar un archivo pdf");
        }

        ResponseEntity<CreatePublicationResponse> createPublicationResponse = pythonDataClient.createPublication(file, payloadJson);
        if (createPublicationResponse.getStatusCode().is2xxSuccessful()) {
            CreatePublicationResponse responseBody = createPublicationResponse.getBody();
            if (responseBody != null && responseBody.id() != null) {
                return Result.success(responseBody);
            } else {
                return Result.failure(Error.failure(createPublicationResponse.getStatusCode().toString(), "No se pudo obtener el ID de la publicación creada"));
            }
        } else {
            return Result.failure(Error.failure(createPublicationResponse.getStatusCode().toString(), "Hubo un problema al crear la publicación"));
        }
    }

    public Result<LoadPublicationsResponse> loadPublications() {
        LoadPublicationRequest request = new LoadPublicationRequest();
        request.setMethod("regex");

        ResponseEntity<LoadPublicationsResponse> response = pythonDataClient.triggerScrape(request);
        if (response.getStatusCode().is2xxSuccessful()) {
            LoadPublicationsResponse responseBody = response.getBody();
            if (responseBody != null) {
                return Result.success(responseBody);
            } else {
                return Result.failure(Error.failure(response.getStatusCode().toString(), "No se pudo obtener la respuesta de la carga de publicaciones"));
            }
        } else {
            return Result.failure(Error.failure(response.getStatusCode().toString(), "Hubo un problema al cargar las publicaciones"));
        }
    }

    private PublicationRequest parseJson(String payloadJson) {
        try {
            return objectMapper.readValue(payloadJson, PublicationRequest.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}