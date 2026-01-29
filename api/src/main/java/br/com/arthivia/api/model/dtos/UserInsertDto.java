package br.com.arthivia.api.model.dtos;


import br.com.arthivia.api.util.Enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInsertDto(
        @NotNull(message = "Invalid name")
        @NotBlank(message = "Invalid name")
        String name,
        @NotNull(message = "Invalid username")
        @NotBlank(message = "Invalid username")
        String username,
        @NotNull(message = "Invalid password")
        @NotBlank(message = "Invalid password")
        String password,
        @NotNull(message = "Invalid role")
        UserRole role,
        @NotNull(message = "Invalid enable status")
        boolean enable
) {
}
