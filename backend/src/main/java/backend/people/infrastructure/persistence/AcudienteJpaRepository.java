package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.AcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface AcudienteJpaRepository extends JpaRepository<AcudienteEntity, UUID> {
    Optional<AcudienteEntity> findByPersonaId(UUID personaId);
}
