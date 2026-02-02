package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.client.create_publication.CreatePublicationResponse;
import com.observatorio.backend_ia.client.load_publication.LoadPublicationsResponse;
import com.observatorio.backend_ia.commons.api.GenericResponse;
import com.observatorio.backend_ia.commons.result.Result;
import com.observatorio.backend_ia.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<CreatePublicationResponse>> create(
            @RequestPart("file") MultipartFile file,
            @RequestPart("payload_json") String payloadJson
    ) {
        Result<CreatePublicationResponse> res = publicationService.save(payloadJson, file);
        return res.isSuccess() ?
                ResponseEntity.ok(GenericResponse.createSuccessResponse(res.getValue())) :
                ResponseEntity.status(400).body(GenericResponse.createErrorResponse(res.getError().getDescription()));
    }

    @PostMapping("/trigger-scrape")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<LoadPublicationsResponse>> triggerScrape() {
        Result<LoadPublicationsResponse> res = publicationService.loadPublications();
        return res.isSuccess() ?
                ResponseEntity.ok(GenericResponse.createSuccessResponse(res.getValue())) :
                ResponseEntity.status(400).body(GenericResponse.createErrorResponse(res.getError().getDescription()));
    }
}