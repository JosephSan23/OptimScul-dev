package backend.people.infrastructure.persistence;

import backend.people.infrastructure.persistence.entity.JornadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JornadaJpaRepository extends JpaRepository<JornadaEntity, UUID> {
}
