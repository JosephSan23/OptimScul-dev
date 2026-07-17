package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfesorJpaRepository extends JpaRepository<ProfesorEntity, UUID> {
    List<ProfesorEntity> findByInstitucionId(UUID institucionId);
    Optional<ProfesorEntity> findByInstitucionIdAndPersonaId(UUID institucionId, UUID personaId);
}