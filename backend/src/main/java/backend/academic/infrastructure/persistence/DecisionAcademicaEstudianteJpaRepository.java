package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.DecisionAcademicaEstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DecisionAcademicaEstudianteJpaRepository extends JpaRepository<DecisionAcademicaEstudianteEntity, UUID> {
}
