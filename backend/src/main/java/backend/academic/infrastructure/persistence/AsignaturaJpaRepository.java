package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.AsignaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AsignaturaJpaRepository extends JpaRepository<AsignaturaEntity, UUID> {
}
