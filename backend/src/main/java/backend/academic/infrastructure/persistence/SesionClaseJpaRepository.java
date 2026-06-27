package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.SesionClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SesionClaseJpaRepository extends JpaRepository<SesionClaseEntity, UUID> {
}
