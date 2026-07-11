package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.SedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SedeJpaRepository extends JpaRepository<SedeEntity, UUID> {
    java.util.List<SedeEntity> findByInstitucionId(UUID institucionId);
}
