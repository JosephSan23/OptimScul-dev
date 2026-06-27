package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.ResultadoAcademico;

@Entity
@Table(name = "decision_academica_estudiante", schema = "optimscul")
public class DecisionAcademicaEstudianteEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Column(name = "anio_lectivo_id")
    private UUID anioLectivoId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado")
    private ResultadoAcademico resultado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_decision")
    private LocalDateTime fechaDecision;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public DecisionAcademicaEstudianteEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getAnioLectivoId() { return anioLectivoId; }
    public void setAnioLectivoId(UUID anioLectivoId) { this.anioLectivoId = anioLectivoId; }
    public UUID getEstudianteId() { return estudianteId; }
    public void setEstudianteId(UUID estudianteId) { this.estudianteId = estudianteId; }
    public ResultadoAcademico getResultado() { return resultado; }
    public void setResultado(ResultadoAcademico resultado) { this.resultado = resultado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDateTime getFechaDecision() { return fechaDecision; }
    public void setFechaDecision(LocalDateTime fechaDecision) { this.fechaDecision = fechaDecision; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
