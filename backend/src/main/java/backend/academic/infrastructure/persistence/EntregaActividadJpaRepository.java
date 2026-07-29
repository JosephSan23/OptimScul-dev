package backend.academic.infrastructure.persistence;

import backend.academic.infrastructure.persistence.entity.EntregaActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;
import java.util.List;

public interface EntregaActividadJpaRepository extends JpaRepository<EntregaActividadEntity, UUID> {
    Optional<EntregaActividadEntity> findByActividadIdAndEstudianteId(UUID actividadId, UUID estudianteId);
    List<EntregaActividadEntity> findByActividadId(UUID actividadId);
}
