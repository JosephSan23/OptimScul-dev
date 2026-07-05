package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PersonaJpaRepository extends JpaRepository<PersonaEntity, UUID> {
    boolean existsByNumeroDocumento(String numeroDocumento);
}
