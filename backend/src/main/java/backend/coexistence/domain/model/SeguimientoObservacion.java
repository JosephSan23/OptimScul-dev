package backend.coexistence.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SeguimientoObservacion {

    private UUID id;
    private UUID observacionId;
    private String comentario;
    private EstadoObservacion estadoAnterior;
    private EstadoObservacion estadoNuevo;
    private UUID creadoPorUsuarioId;
    private LocalDateTime createdAt;

    public SeguimientoObservacion() {}

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
