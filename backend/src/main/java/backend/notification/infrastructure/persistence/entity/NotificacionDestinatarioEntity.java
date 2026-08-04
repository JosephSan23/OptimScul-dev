package backend.notification.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.notification.domain.model.CanalNotificacion;
import backend.notification.domain.model.EstadoNotificacion;

@Getter
@Setter
@NoArgsConstructor
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
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "canal", columnDefinition = "canal_notificacion_enum")
    private CanalNotificacion canal;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "estado", columnDefinition = "estado_notificacion_enum")
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

}
