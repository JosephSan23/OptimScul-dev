package backend.coexistence.infrastructure.persistence;

import backend.coexistence.infrastructure.persistence.entity.TipoObservacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TipoObservacionJpaRepository extends JpaRepository<TipoObservacionEntity, UUID> {
}
