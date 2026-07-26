// controller/dto/ResourceResponse.java
package com.observatorio.backend_ia.controller.dto;

import com.observatorio.backend_ia.model.enums.ResourceTopic;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResourceResponse {
    private Long id;
    private String title;
    private String description;
    private String type;
    private String url;
    private String source;
    private ResourceTopic topic;
    private boolean featured;
    private LocalDateTime createdAt;
}