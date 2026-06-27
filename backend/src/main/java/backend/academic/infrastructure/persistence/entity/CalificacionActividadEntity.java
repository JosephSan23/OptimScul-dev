package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calificacion_actividad", schema = "optimscul")
public class CalificacionActividadEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "actividad_id")
    private UUID actividadId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "entrega_actividad_id")
    private UUID entregaActividadId;

    @Column(name = "nota_obtenida")
    private BigDecimal notaObtenida;

    @Column(name = "observacion_docente")
    private String observacionDocente;

    @Column(name = "calificada_por_usuario_id")
    private UUID calificadaPorUsuarioId;

    @Column(name = "fecha_calificacion")
    private LocalDateTime fechaCalificacion;

    @Column(name = "es_recuperacion")
    private Boolean esRecuperacion;

    @Column(name = "anulada")
    private Boolean anulada;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CalificacionActividadEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
