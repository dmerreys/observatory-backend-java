package com.observatorio.backend_ia.controller;

import com.observatorio.backend_ia.model.enums.IdeaStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIdeaStatusRequest {

    @NotNull(message = "status is required")
    private IdeaStatus status;
}
