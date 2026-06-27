package backend.coexistence.infrastructure.persistence;

import backend.coexistence.infrastructure.persistence.entity.SeguimientoObservacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SeguimientoObservacionJpaRepository extends JpaRepository<SeguimientoObservacionEntity, UUID> {
}
