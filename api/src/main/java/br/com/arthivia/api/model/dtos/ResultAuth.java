package br.com.arthivia.api.model.dtos;

public record ResultAuth(
        String token,
        AuthResponseDto authResponseDto
) {}
