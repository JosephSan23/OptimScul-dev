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
}