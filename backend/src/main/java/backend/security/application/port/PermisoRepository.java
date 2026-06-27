package backend.security.application.port;

import backend.security.domain.model.Permiso;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermisoRepository {

    Permiso save(Permiso entity);
    Optional<Permiso> findById(UUID id);
    List<Permiso> findAll();
    void deleteById(UUID id);
}
