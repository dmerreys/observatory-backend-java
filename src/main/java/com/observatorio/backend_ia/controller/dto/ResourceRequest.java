// controller/dto/ResourceRequest.java
package com.observatorio.backend_ia.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResourceRequest {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String type; // video, pdf, link

    @NotBlank
    private String url;

    private String source;
}