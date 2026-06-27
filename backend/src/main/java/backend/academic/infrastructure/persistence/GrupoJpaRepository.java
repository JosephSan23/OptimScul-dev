package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.GrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GrupoJpaRepository extends JpaRepository<GrupoEntity, UUID> {
}
