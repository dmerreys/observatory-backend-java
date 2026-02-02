package com.observatorio.backend_ia.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @NotBlank(message = "name is required")
    private String name;

    // idea and ethicalConcern can be large texts
    @NotBlank(message = "idea is required")
    private String idea;

    private String ethicalConcern;
}
