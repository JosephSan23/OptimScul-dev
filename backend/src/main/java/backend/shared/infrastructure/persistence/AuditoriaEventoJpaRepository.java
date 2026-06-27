package backend.shared.infrastructure.persistence;

import backend.shared.infrastructure.persistence.entity.AuditoriaEventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AuditoriaEventoJpaRepository extends JpaRepository<AuditoriaEventoEntity, UUID> {
}
