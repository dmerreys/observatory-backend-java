package com.observatorio.backend_ia.client;

import com.observatorio.backend_ia.client.create_publication.CreatePublicationResponse;
import com.observatorio.backend_ia.client.load_publication.LoadPublicationRequest;
import com.observatorio.backend_ia.client.load_publication.LoadPublicationsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "python-scraper", url = "${python.service.url}")
public interface PythonDataClient {

    @PostMapping(
            value = "/publications",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CreatePublicationResponse> createPublication(
            @RequestPart("file") MultipartFile file,
            @RequestPart("payload_json") String payloadJson
    );

    @PostMapping("/load")
    ResponseEntity<LoadPublicationsResponse> triggerScrape(@RequestBody LoadPublicationRequest request);
}