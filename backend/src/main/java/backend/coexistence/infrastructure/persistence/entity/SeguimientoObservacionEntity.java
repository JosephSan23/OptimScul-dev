package backend.coexistence.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.coexistence.domain.model.EstadoObservacion;

@Entity
@Table(name = "seguimiento_observacion", schema = "optimscul")
public class SeguimientoObservacionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "observacion_id")
    private UUID observacionId;

    @Column(name = "comentario")
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior")
    private EstadoObservacion estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo")
    private EstadoObservacion estadoNuevo;

    @Column(name = "creado_por_usuario_id")
    private UUID creadoPorUsuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public SeguimientoObservacionEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getObservacionId() { return observacionId; }
    public void setObservacionId(UUID observacionId) { this.observacionId = observacionId; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public EstadoObservacion getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoObservacion estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public EstadoObservacion getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoObservacion estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public UUID getCreadoPorUsuarioId() { return creadoPorUsuarioId; }
    public void setCreadoPorUsuarioId(UUID creadoPorUsuarioId) { this.creadoPorUsuarioId = creadoPorUsuarioId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
