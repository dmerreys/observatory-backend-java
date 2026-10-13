package com.observatorio.backend_ia.model.enums;

public enum IdeaCategory {
    SUGERENCIA,
    COMENTARIO,
    REPORTE_ERROR;

    public boolean allowsEthicalConcern() {
        return this == SUGERENCIA;
    }

    public boolean allowsDoneStatus() {
        return this != COMENTARIO;
    }
}
