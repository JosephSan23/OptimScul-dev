package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.EntregaActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EntregaActividadJpaRepository extends JpaRepository<EntregaActividadEntity, UUID> {
}
