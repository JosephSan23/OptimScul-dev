package backend.notification.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import backend.notification.domain.model.TipoNotificacion;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notificacion", schema = "optimscul")
public class NotificacionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institucion_id")
    private UUID institucionId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo", columnDefinition = "tipo_notificacion_enum")
    private TipoNotificacion tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "modulo_relacionado")
    private String moduloRelacionado;

    @Column(name = "entidad_relacionada_id")
    private UUID entidadRelacionadaId;

    @Column(name = "prioridad")
    private Short prioridad;

    @Column(name = "programada_para")
    private LocalDateTime programadaPara;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
