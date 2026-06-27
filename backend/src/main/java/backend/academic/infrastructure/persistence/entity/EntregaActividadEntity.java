package backend.academic.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.academic.domain.model.EstadoEntregaActividad;

@Entity
@Table(name = "entrega_actividad", schema = "optimscul")
public class EntregaActividadEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "actividad_id")
    private UUID actividadId;

    @Column(name = "estudiante_id")
    private UUID estudianteId;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "comentario_estudiante")
    private String comentarioEstudiante;

    @Column(name = "archivo_url")
    private String archivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEntregaActividad estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EntregaActividadEntity() {}

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
