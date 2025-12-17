package br.com.arthivia.api.util;

public record ErrorResponse(
        Integer statusCode,
        String message
) {}
