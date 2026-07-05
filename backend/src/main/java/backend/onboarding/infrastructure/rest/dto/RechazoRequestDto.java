package backend.onboarding.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public class RechazoRequestDto {

    @NotBlank
    private String motivo;

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}