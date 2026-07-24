package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface EstudianteJpaRepository extends JpaRepository<EstudianteEntity, UUID> {
    List<EstudianteEntity> findByInstitucionId(UUID institucionId);
    Optional<EstudianteEntity> findByInstitucionIdAndPersonaId(UUID institucionId, UUID personaId);
}
