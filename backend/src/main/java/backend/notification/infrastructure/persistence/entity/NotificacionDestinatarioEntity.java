package backend.notification.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.notification.domain.model.CanalNotificacion;
import backend.notification.domain.model.EstadoNotificacion;

@Entity
@Table(name = "notificacion_destinatario", schema = "optimscul")
public class NotificacionDestinatarioEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "notificacion_id")
    private UUID notificacionId;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal")
    private CanalNotificacion canal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoNotificacion estado;

    @Column(name = "enviada_en")
    private LocalDateTime enviadaEn;

    @Column(name = "leida_en")
    private LocalDateTime leidaEn;

    @Column(name = "error_envio")
    private String errorEnvio;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public NotificacionDestinatarioEntity() {}

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
