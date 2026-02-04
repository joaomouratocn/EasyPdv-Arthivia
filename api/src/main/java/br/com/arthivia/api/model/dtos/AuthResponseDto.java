package br.com.arthivia.api.model.dtos;

public record AuthResponseDto(
        String name,
        String token,
        Boolean mustChange
){}
