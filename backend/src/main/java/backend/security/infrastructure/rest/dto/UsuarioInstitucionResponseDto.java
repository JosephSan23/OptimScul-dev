package backend.security.infrastructure.rest.dto;

import java.util.UUID;

public class UsuarioInstitucionResponseDto {

    private UUID usuarioId;

    private UUID institucionId;

    private Boolean esPrincipal;

    private Boolean activo;

    public UsuarioInstitucionResponseDto() {}

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
