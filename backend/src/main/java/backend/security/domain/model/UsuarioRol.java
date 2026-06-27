package backend.security.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioRol {

    private UUID id;
    private UUID usuarioId;
    private UUID institucionId;
    private UUID rolId;
    private Boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UsuarioRol() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
