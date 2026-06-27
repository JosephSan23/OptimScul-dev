package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProfesorJpaRepository extends JpaRepository<ProfesorEntity, UUID> {
}
