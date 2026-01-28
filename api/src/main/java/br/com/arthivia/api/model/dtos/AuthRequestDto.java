package br.com.arthivia.api.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
        @NotNull(message = "Invalid username")
        @NotBlank(message = "Invalid username")
        String username,
        @NotNull(message = "Invalid password")
        @NotBlank(message = "Invalid password")
        String password
){}
