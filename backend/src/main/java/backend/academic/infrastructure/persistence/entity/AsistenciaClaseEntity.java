package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.TipoAsistencia;

@Entity
@Table(name = "asistencia_clase", schema = "optimscul")
public class AsistenciaClaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "sesion_clase_id")
    private UUID sesionClaseId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_asistencia")
    private TipoAsistencia tipoAsistencia;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "justificacion")
    private String justificacion;

    @Column(name = "minutos_tarde")
    private Short minutosTarde;

    @Column(name = "registrada_por_usuario_id")
    private UUID registradaPorUsuarioId;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public AsistenciaClaseEntity() {}

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
