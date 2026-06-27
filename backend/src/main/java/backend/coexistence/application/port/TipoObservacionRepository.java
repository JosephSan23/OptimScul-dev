package backend.coexistence.application.port;

import backend.coexistence.domain.model.TipoObservacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoObservacionRepository {

    TipoObservacion save(TipoObservacion entity);
    Optional<TipoObservacion> findById(UUID id);
    List<TipoObservacion> findAll();
    void deleteById(UUID id);
}
