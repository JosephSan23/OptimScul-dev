package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.ResumenAnualEstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ResumenAnualEstudianteJpaRepository extends JpaRepository<ResumenAnualEstudianteEntity, UUID> {
}
