package backend.notification.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificacionDestinatario {

    private UUID id;
    private UUID notificacionId;
    private UUID usuarioId;
    private CanalNotificacion canal;
    private EstadoNotificacion estado;
    private LocalDateTime enviadaEn;
    private LocalDateTime leidaEn;
    private String errorEnvio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NotificacionDestinatario() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
