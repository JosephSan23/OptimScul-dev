package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.CalificacionActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface CalificacionActividadJpaRepository extends JpaRepository<CalificacionActividadEntity, UUID> {
    List<CalificacionActividadEntity> findByActividadId(UUID actividadId);
    Optional<CalificacionActividadEntity> findByActividadIdAndEstudianteId(UUID actividadId, UUID estudianteId);
    boolean existsByActividadId(UUID actividadId);
}
