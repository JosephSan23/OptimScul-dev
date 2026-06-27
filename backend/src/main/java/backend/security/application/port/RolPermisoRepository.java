package backend.security.application.port;

import backend.security.domain.model.RolPermiso;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolPermisoRepository {

    RolPermiso save(RolPermiso entity);
    Optional<RolPermiso> findById(UUID id);
    List<RolPermiso> findAll();
    void deleteById(UUID id);
}
