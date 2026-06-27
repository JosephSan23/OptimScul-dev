package backend.coexistence.infrastructure.rest.dto;

import java.util.UUID;
import backend.coexistence.domain.model.EstadoObservacion;

public class SeguimientoObservacionResponseDto {

    private UUID observacionId;

    private String comentario;

    private EstadoObservacion estadoAnterior;

    private EstadoObservacion estadoNuevo;

    private UUID creadoPorUsuarioId;

    public SeguimientoObservacionResponseDto() {}

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
}
