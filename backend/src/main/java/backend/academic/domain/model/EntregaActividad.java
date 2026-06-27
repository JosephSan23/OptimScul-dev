package backend.academic.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class EntregaActividad {

    private UUID id;
    private UUID actividadId;
    private UUID estudianteId;
    private LocalDateTime fechaEntrega;
    private String comentarioEstudiante;
    private String archivoUrl;
    private EstadoEntregaActividad estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EntregaActividad() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
