package com.observatorio.backend_ia.client.create_publication;

import jakarta.validation.constraints.NotBlank;

public record ContributorRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "role is required")
        String role
) {
}
