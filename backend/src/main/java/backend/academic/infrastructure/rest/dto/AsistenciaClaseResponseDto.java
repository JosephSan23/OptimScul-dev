package backend.academic.infrastructure.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.TipoAsistencia;

public class AsistenciaClaseResponseDto {

    private UUID sesionClaseId;

    private UUID estudianteId;

    private TipoAsistencia tipoAsistencia;

    private String observacion;

    private String justificacion;

    private Integer minutosTarde;

    private UUID registradaPorUsuarioId;

    private LocalDateTime fechaRegistro;

    public AsistenciaClaseResponseDto() {}

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
    public Integer getMinutosTarde() { return minutosTarde; }
    public void setMinutosTarde(Integer minutosTarde) { this.minutosTarde = minutosTarde; }
    public UUID getRegistradaPorUsuarioId() { return registradaPorUsuarioId; }
    public void setRegistradaPorUsuarioId(UUID registradaPorUsuarioId) { this.registradaPorUsuarioId = registradaPorUsuarioId; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
