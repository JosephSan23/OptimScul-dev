package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.ActividadAcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ActividadAcademicaJpaRepository extends JpaRepository<ActividadAcademicaEntity, UUID> {
}
