package backend.admission.application.port;

import backend.admission.domain.model.SeguimientoPostulacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeguimientoPostulacionRepository {

    SeguimientoPostulacion save(SeguimientoPostulacion entity);
    Optional<SeguimientoPostulacion> findById(UUID id);
    List<SeguimientoPostulacion> findAll();
    void deleteById(UUID id);
}
