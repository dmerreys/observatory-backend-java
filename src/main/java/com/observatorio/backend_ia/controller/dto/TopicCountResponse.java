package com.observatorio.backend_ia.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicCountResponse {
    private String topic;
    private long count;
}
