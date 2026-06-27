package backend.security.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class UsuarioRolRequestDto {

    @NotNull
    private UUID usuarioId;

    private UUID institucionId;

    @NotNull
    private UUID rolId;

    @NotNull
    private Boolean activo;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    public UsuarioRolRequestDto() {}

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getRolId() { return rolId; }
    public void setRolId(UUID rolId) { this.rolId = rolId; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}
