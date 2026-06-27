package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.ResumenPeriodoEstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ResumenPeriodoEstudianteJpaRepository extends JpaRepository<ResumenPeriodoEstudianteEntity, UUID> {
}
