package com.observatorio.backend_ia.client.create_publication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PublicationRequest(
        @NotBlank(message = "title is required")
        String title,
        @NotBlank(message = "abstractText is required")
        @JsonProperty(value = "abstract")
        String abstractText,
        @NotEmpty(message = "subjects must not be empty")
        List<@NotBlank(message = "subject must not be blank") String> subjects,
        @NotEmpty(message = "contributors must not be empty")
        @Valid
        List<ContributorRequest> contributors
) {
}
