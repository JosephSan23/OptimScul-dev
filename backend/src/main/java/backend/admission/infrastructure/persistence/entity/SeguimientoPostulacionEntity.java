package backend.admission.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.admission.domain.model.EstadoPostulacion;

@Entity
@Table(name = "seguimiento_postulacion", schema = "optimscul")
public class SeguimientoPostulacionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "postulacion_id")
    private UUID postulacionId;

    @Column(name = "comentario")
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior")
    private EstadoPostulacion estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo")
    private EstadoPostulacion estadoNuevo;

    @Column(name = "creado_por_usuario_id")
    private UUID creadoPorUsuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public SeguimientoPostulacionEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPostulacionId() { return postulacionId; }
    public void setPostulacionId(UUID postulacionId) { this.postulacionId = postulacionId; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public EstadoPostulacion getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(EstadoPostulacion estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public EstadoPostulacion getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(EstadoPostulacion estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public UUID getCreadoPorUsuarioId() { return creadoPorUsuarioId; }
    public void setCreadoPorUsuarioId(UUID creadoPorUsuarioId) { this.creadoPorUsuarioId = creadoPorUsuarioId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
