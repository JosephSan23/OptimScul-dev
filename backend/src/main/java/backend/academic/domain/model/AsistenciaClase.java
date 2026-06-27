package backend.academic.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AsistenciaClase {

    private UUID id;
    private UUID sesionClaseId;
    private UUID estudianteId;
    private TipoAsistencia tipoAsistencia;
    private String observacion;
    private String justificacion;
    private Short minutosTarde;
    private UUID registradaPorUsuarioId;
    private LocalDateTime fechaRegistro;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AsistenciaClase() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getSesionClaseId() { return sesionClaseId; }
    public void setSesionClaseId(UUID sesionClaseId) { this.sesionClaseId = sesionClaseId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public TipoAsistencia getTipoAsistencia() { return tipoAsistencia; }
    public void setTipoAsistencia(TipoAsistencia tipoAsistencia) { this.tipoAsistencia = tipoAsistencia; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
    public Short getMinutosTarde() { return minutosTarde; }
    public void setMinutosTarde(Short minutosTarde) { this.minutosTarde = minutosTarde; }
    public UUID getRegistradaPorUsuarioId() { return registradaPorUsuarioId; }
    public void setRegistradaPorUsuarioId(UUID registradaPorUsuarioId) { this.registradaPorUsuarioId = registradaPorUsuarioId; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
