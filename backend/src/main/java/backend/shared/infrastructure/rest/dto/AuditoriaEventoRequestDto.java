package backend.shared.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import backend.shared.AccionAuditoria;

public class AuditoriaEventoRequestDto {

    private UUID institucionId;

    private UUID usuarioId;

    @NotNull
    private String modulo;

    @NotNull
    private String entidad;

    private UUID entidadId;

    @NotNull
    private AccionAuditoria accion;

    private String descripcion;

    private String valoresAntes;

    private String valoresDespues;

    private String ip;

    private String userAgent;

    public AuditoriaEventoRequestDto() {}

    public UUID getInstitucionId() { return institucionId; }
    public void setInstitucionId(UUID institucionId) { this.institucionId = institucionId; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }
    public UUID getEntidadId() { return entidadId; }
    public void setEntidadId(UUID entidadId) { this.entidadId = entidadId; }
    public AccionAuditoria getAccion() { return accion; }
    public void setAccion(AccionAuditoria accion) { this.accion = accion; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getValoresAntes() { return valoresAntes; }
    public void setValoresAntes(String valoresAntes) { this.valoresAntes = valoresAntes; }
    public String getValoresDespues() { return valoresDespues; }
    public void setValoresDespues(String valoresDespues) { this.valoresDespues = valoresDespues; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
