package com.observatorio.backend_ia.commons.api;

import lombok.Data;

import java.util.List;

@Data
public class GenericResponse<T> {
    private String status;
    private T data;
    private List<String> messages;

    private GenericResponse(String status, T data, List<String> messages) {
        this.status = status;
        this.data = data;
        this.messages = messages;
    }

    public static <T> GenericResponse<T> createSuccessResponse(T data) {
        return new GenericResponse<>("OK", data, List.of());
    }

    public static <T> GenericResponse<T> createSuccessResponse(T data, List<String> messages) {
        return new GenericResponse<>("OK", data, messages);
    }

    public static <T> GenericResponse<T> createSuccessResponse() {
        return new GenericResponse<>("OK", null, List.of());
    }

    public static <T> GenericResponse<T> createErrorResponse(String errorMessage) {
        return new GenericResponse<>("ERROR", null, List.of(errorMessage));
    }
}
