package br.com.arthivia.api.model.dtos;

import br.com.arthivia.api.util.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInsertDto(
        @NotNull(message = "Invalid name")
        @NotBlank(message = "Invalid name")
        String name,
        @NotNull(message = "Invalid login")
        @NotBlank(message = "Invalid login")
        String login,
        @NotNull(message = "Invalid password")
        @NotBlank(message = "Invalid password")
        String password,
        @NotNull(message = "Invalid role")
        UserRole role,
        @NotNull(message = "Invalid enable status")
        boolean enable
) {}
