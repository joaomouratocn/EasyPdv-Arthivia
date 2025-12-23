package br.com.arthivia.api.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryInsertDto(
        @NotNull
        @NotBlank
        String categoryName
) {}
