package backend.admission.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SeguimientoPostulacion {

    private UUID id;
    private UUID postulacionId;
    private String comentario;
    private EstadoPostulacion estadoAnterior;
    private EstadoPostulacion estadoNuevo;
    private UUID creadoPorUsuarioId;
    private LocalDateTime createdAt;

    public SeguimientoPostulacion() {}

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
