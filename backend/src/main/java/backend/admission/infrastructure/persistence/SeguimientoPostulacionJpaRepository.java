package backend.admission.infrastructure.persistence;

import backend.admission.infrastructure.persistence.entity.SeguimientoPostulacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SeguimientoPostulacionJpaRepository extends JpaRepository<SeguimientoPostulacionEntity, UUID> {
}
