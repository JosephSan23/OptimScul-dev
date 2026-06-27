package backend.academic.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoEntregaActividad;

public class EntregaActividadRequestDto {

    @NotNull
    private UUID actividadId;

    @NotNull
    private UUID estudianteId;

    private LocalDateTime fechaEntrega;

    private String comentarioEstudiante;

    private String archivoUrl;

    @NotNull
    private EstadoEntregaActividad estado;

    public EntregaActividadRequestDto() {}

    public UUID getActividadId() { return actividadId; }
    public void setActividadId(UUID actividadId) { this.actividadId = actividadId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getComentarioEstudiante() { return comentarioEstudiante; }
    public void setComentarioEstudiante(String comentarioEstudiante) { this.comentarioEstudiante = comentarioEstudiante; }
    public String getArchivoUrl() { return archivoUrl; }
    public void setArchivoUrl(String archivoUrl) { this.archivoUrl = archivoUrl; }
    public EstadoEntregaActividad getEstado() { return estado; }
    public void setEstado(EstadoEntregaActividad estado) { this.estado = estado; }
}
