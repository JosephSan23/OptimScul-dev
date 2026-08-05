package backend.profile.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarPasswordRequest(
        @NotBlank String actual,
        @NotBlank @Size(min = 8, message = "La nueva contraseña debe tener al menos 8 caracteres") String nueva) {}