package backend.academic.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CalificacionActividadResponseDto {

    private UUID actividadId;

    private UUID estudianteId;

    private UUID entregaActividadId;

    private BigDecimal notaObtenida;

    private String observacionDocente;

    private UUID calificadaPorUsuarioId;

    private LocalDateTime fechaCalificacion;

    private Boolean esRecuperacion;

    private Boolean anulada;

    public CalificacionActividadResponseDto() {}

    public UUID getActividadId() { return actividadId; }
    public void setActividadId(UUID actividadId) { this.actividadId = actividadId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public UUID getEntregaActividadId() { return entregaActividadId; }
    public void setEntregaActividadId(UUID entregaActividadId) { this.entregaActividadId = entregaActividadId; }
    public BigDecimal getNotaObtenida() { return notaObtenida; }
    public void setNotaObtenida(BigDecimal notaObtenida) { this.notaObtenida = notaObtenida; }
    public String getObservacionDocente() { return observacionDocente; }
    public void setObservacionDocente(String observacionDocente) { this.observacionDocente = observacionDocente; }
    public UUID getCalificadaPorUsuarioId() { return calificadaPorUsuarioId; }
    public void setCalificadaPorUsuarioId(UUID calificadaPorUsuarioId) { this.calificadaPorUsuarioId = calificadaPorUsuarioId; }
    public LocalDateTime getFechaCalificacion() { return fechaCalificacion; }
    public void setFechaCalificacion(LocalDateTime fechaCalificacion) { this.fechaCalificacion = fechaCalificacion; }
    public Boolean getEsRecuperacion() { return esRecuperacion; }
    public void setEsRecuperacion(Boolean esRecuperacion) { this.esRecuperacion = esRecuperacion; }
    public Boolean getAnulada() { return anulada; }
    public void setAnulada(Boolean anulada) { this.anulada = anulada; }
}
