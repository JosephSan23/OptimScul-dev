package backend.chat.infrastructure.persistence;

import backend.chat.infrastructure.persistence.entity.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MensajeJpaRepository extends JpaRepository<MensajeEntity, UUID> {

    List<MensajeEntity> findByConversacionIdOrderByCreatedAtAsc(UUID conversacionId);

    Optional<MensajeEntity> findFirstByConversacionIdOrderByCreatedAtDesc(UUID conversacionId);

    long countByConversacionIdAndRemitenteIdNotAndLeidoFalse(UUID conversacionId, UUID remitenteId);

    @Modifying
    @Query("update MensajeEntity m set m.leido = true " +
           "where m.conversacionId = :conversacionId and m.remitenteId <> :paraUsuarioId and m.leido = false")
    void marcarLeidos(@Param("conversacionId") UUID conversacionId, @Param("paraUsuarioId") UUID paraUsuarioId);

    @Query(value = "SELECT DISTINCT ON (m.conversacion_id) m.* FROM optimscul.mensaje m " +
           "WHERE m.conversacion_id IN (:ids) ORDER BY m.conversacion_id, m.created_at DESC", nativeQuery = true)
    List<MensajeEntity> findUltimosPorConversaciones(@Param("ids") List<UUID> ids);

    @Query(value = "SELECT m.conversacion_id AS cid, COUNT(*) AS total FROM optimscul.mensaje m " +
           "WHERE m.conversacion_id IN (:ids) AND m.remitente_id <> :usuario AND m.leido = false " +
           "GROUP BY m.conversacion_id", nativeQuery = true)
    List<Object[]> contarNoLeidosPorConversaciones(@Param("ids") List<UUID> ids, @Param("usuario") UUID usuario);
}
