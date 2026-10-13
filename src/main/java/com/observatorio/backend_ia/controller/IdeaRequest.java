package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.model.enums.IdeaCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "idea is required")
    private String idea;

    @NotNull(message = "category is required")
    private IdeaCategory category;

    private String ethicalConcern;
}
