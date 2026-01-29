package com.observatorio.backend_ia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "python-scraper", url = "${python.service.url}")
public interface PythonDataClient {

    @GetMapping("/publications")
    ResponseEntity<String> getAllPublications();

    @GetMapping("/publications/{id}")
    ResponseEntity<String> getPublicationById(@PathVariable String id);

    @PostMapping("/publications")
    ResponseEntity<String> createPublication(@RequestBody String publicationJson);

    @PostMapping("/schedule.json")
    ResponseEntity<String> triggerScrape(@RequestParam("project") String project, @RequestParam("spider") String spider);
}