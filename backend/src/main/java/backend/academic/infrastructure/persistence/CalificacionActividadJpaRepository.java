package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.CalificacionActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CalificacionActividadJpaRepository extends JpaRepository<CalificacionActividadEntity, UUID> {
}
