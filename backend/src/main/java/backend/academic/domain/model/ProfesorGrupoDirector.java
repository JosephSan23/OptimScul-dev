package backend.academic.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProfesorGrupoDirector {

    private UUID id;
    private UUID grupoId;
    private UUID profesorId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;
    private LocalDateTime createdAt;

    public ProfesorGrupoDirector() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getGrupoId() { return grupoId; }
    public void setGrupoId(UUID grupoId) { this.grupoId = grupoId; }
    public UUID getProfesorId() { return profesorId; }
    public void setProfesorId(UUID profesorId) { this.profesorId = profesorId; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
