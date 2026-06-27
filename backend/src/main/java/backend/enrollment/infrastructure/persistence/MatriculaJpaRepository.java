package backend.enrollment.infrastructure.persistence;

import backend.enrollment.infrastructure.persistence.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, UUID> {
}
