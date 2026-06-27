package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.HorarioCargaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HorarioCargaJpaRepository extends JpaRepository<HorarioCargaEntity, UUID> {
}
