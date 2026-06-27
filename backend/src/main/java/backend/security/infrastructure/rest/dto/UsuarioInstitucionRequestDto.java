package backend.security.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UsuarioInstitucionRequestDto {

    @NotNull
    private UUID usuarioId;

    @NotNull
    private UUID institucionId;

    @NotNull
    private Boolean esPrincipal;

    @NotNull
    private Boolean activo;

    public UsuarioInstitucionRequestDto() {}

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
