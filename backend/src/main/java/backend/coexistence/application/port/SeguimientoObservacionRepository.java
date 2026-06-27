package backend.coexistence.application.port;

import backend.coexistence.domain.model.SeguimientoObservacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeguimientoObservacionRepository {

    SeguimientoObservacion save(SeguimientoObservacion entity);
    Optional<SeguimientoObservacion> findById(UUID id);
    List<SeguimientoObservacion> findAll();
    void deleteById(UUID id);
}
