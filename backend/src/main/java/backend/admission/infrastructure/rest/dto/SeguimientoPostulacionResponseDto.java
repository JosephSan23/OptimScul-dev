package backend.admission.infrastructure.rest.dto;

import java.util.UUID;
import backend.admission.domain.model.EstadoPostulacion;

public class SeguimientoPostulacionResponseDto {

    private UUID postulacionId;

    private String comentario;

    private EstadoPostulacion estadoAnterior;

    private EstadoPostulacion estadoNuevo;

    private UUID creadoPorUsuarioId;

    public SeguimientoPostulacionResponseDto() {}

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
}
