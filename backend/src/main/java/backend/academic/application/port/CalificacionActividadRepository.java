package backend.academic.application.port;

import backend.academic.domain.model.CalificacionActividad;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CalificacionActividadRepository {

    CalificacionActividad save(CalificacionActividad entity);
    Optional<CalificacionActividad> findById(UUID id);
    List<CalificacionActividad> findAll();
    void deleteById(UUID id);
    List<CalificacionActividad> findByActividadId(UUID actividadId);
    Optional<CalificacionActividad> findByActividadIdAndEstudianteId(UUID actividadId, UUID estudianteId);
    boolean existsByActividadId(UUID actividadId);
}
