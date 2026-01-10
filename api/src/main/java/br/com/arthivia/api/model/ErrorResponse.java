package br.com.arthivia.api.model;

public record ErrorResponse(
        Integer statusCode,
        String message
) {}
