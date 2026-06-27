package backend.coexistence.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import backend.coexistence.domain.model.EstadoObservacion;

public class SeguimientoObservacionRequestDto {

    @NotNull
    private UUID observacionId;

    @NotNull
    private String comentario;

    private EstadoObservacion estadoAnterior;

    private EstadoObservacion estadoNuevo;

    private UUID creadoPorUsuarioId;

    public SeguimientoObservacionRequestDto() {}

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
