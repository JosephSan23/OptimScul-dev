package backend.notification.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.notification.domain.model.CanalNotificacion;
import backend.notification.domain.model.EstadoNotificacion;

public class NotificacionDestinatarioRequestDto {

    @NotNull
    private UUID notificacionId;

    @NotNull
    private UUID usuarioId;

    @NotNull
    private CanalNotificacion canal;

    @NotNull
    private EstadoNotificacion estado;

    private LocalDateTime enviadaEn;

    private LocalDateTime leidaEn;

    private String errorEnvio;

    public NotificacionDestinatarioRequestDto() {}

    public UUID getNotificacionId() { return notificacionId; }
    public void setNotificacionId(UUID notificacionId) { this.notificacionId = notificacionId; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public CanalNotificacion getCanal() { return canal; }
    public void setCanal(CanalNotificacion canal) { this.canal = canal; }
    public EstadoNotificacion getEstado() { return estado; }
    public void setEstado(EstadoNotificacion estado) { this.estado = estado; }
    public LocalDateTime getEnviadaEn() { return enviadaEn; }
    public void setEnviadaEn(LocalDateTime enviadaEn) { this.enviadaEn = enviadaEn; }
    public LocalDateTime getLeidaEn() { return leidaEn; }
    public void setLeidaEn(LocalDateTime leidaEn) { this.leidaEn = leidaEn; }
    public String getErrorEnvio() { return errorEnvio; }
    public void setErrorEnvio(String errorEnvio) { this.errorEnvio = errorEnvio; }
}
