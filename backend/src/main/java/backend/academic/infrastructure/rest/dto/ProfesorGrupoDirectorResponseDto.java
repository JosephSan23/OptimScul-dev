package backend.academic.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ProfesorGrupoDirectorResponseDto {

    private UUID grupoId;

    private UUID profesorId;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Boolean activo;

    public ProfesorGrupoDirectorResponseDto() {}

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
}
