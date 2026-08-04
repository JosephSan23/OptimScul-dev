package backend.notification.infrastructure.persistence;

import backend.notification.infrastructure.persistence.entity.NotificacionDestinatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificacionDestinatarioJpaRepository
        extends JpaRepository<NotificacionDestinatarioEntity, UUID> {

    interface BandejaRow {
        UUID getId();
        UUID getNotificacionId();
        String getTipo();
        String getTitulo();
        String getMensaje();
        String getModuloRelacionado();
        UUID getEntidadRelacionadaId();
        Short getPrioridad();
        String getEstado();
        LocalDateTime getLeidaEn();
        LocalDateTime getCreatedAt();
    }

    @Query(value = """
        SELECT nd.id AS id, n.id AS notificacionId, CAST(n.tipo AS text) AS tipo,
               n.titulo AS titulo, n.mensaje AS mensaje, n.modulo_relacionado AS moduloRelacionado,
               n.entidad_relacionada_id AS entidadRelacionadaId, n.prioridad AS prioridad,
               CAST(nd.estado AS text) AS estado, nd.leida_en AS leidaEn, n.created_at AS createdAt
        FROM optimscul.notificacion_destinatario nd
        JOIN optimscul.notificacion n ON n.id = nd.notificacion_id
        WHERE nd.usuario_id = :uid AND nd.canal = 'IN_APP'
        ORDER BY n.created_at DESC
        LIMIT :limite
        """, nativeQuery = true)
    List<BandejaRow> bandeja(@Param("uid") UUID uid, @Param("limite") int limite);

    @Query(value = "SELECT COUNT(*) FROM optimscul.notificacion_destinatario " +
                   "WHERE usuario_id = :uid AND canal = 'IN_APP' AND estado <> 'LEIDA'", nativeQuery = true)
    long contarNoLeidas(@Param("uid") UUID uid);

    @Modifying
    @Query(value = "UPDATE optimscul.notificacion_destinatario SET estado='LEIDA', leida_en=now(), updated_at=now() " +
                   "WHERE id = :id AND usuario_id = :uid", nativeQuery = true)
    int marcarLeida(@Param("id") UUID id, @Param("uid") UUID uid);

    @Modifying
    @Query(value = "UPDATE optimscul.notificacion_destinatario SET estado='LEIDA', leida_en=now(), updated_at=now() " +
                   "WHERE usuario_id = :uid AND canal='IN_APP' AND estado <> 'LEIDA'", nativeQuery = true)
    int marcarTodas(@Param("uid") UUID uid);

    @Modifying
    @Query(value = """
        UPDATE optimscul.notificacion_destinatario nd
        SET estado='LEIDA', leida_en=now(), updated_at=now()
        FROM optimscul.notificacion n
        WHERE nd.notificacion_id = n.id AND nd.usuario_id = :uid AND nd.canal='IN_APP'
              AND nd.estado <> 'LEIDA' AND n.modulo_relacionado = 'chat'
              AND n.entidad_relacionada_id = :convId
        """, nativeQuery = true)
    int marcarLeidasDeConversacion(@Param("uid") UUID uid, @Param("convId") UUID convId);
}