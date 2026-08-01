package backend.chat.infrastructure.persistence;

import backend.chat.infrastructure.persistence.entity.ConversacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversacionJpaRepository extends JpaRepository<ConversacionEntity, UUID> {

    Optional<ConversacionEntity> findByInstitucionIdAndUsuarioMenorIdAndUsuarioMayorId(
            UUID institucionId, UUID usuarioMenorId, UUID usuarioMayorId);

    List<ConversacionEntity> findByUsuarioMenorIdOrUsuarioMayorId(UUID menor, UUID mayor);
}