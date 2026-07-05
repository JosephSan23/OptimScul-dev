package backend.security.application.port;

import backend.security.domain.model.Rol;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolRepository {

    Rol save(Rol entity);
    Optional<Rol> findById(UUID id);
    List<Rol> findAll();
    void deleteById(UUID id);
    Optional<Rol> findByCodigo(String codigo);
}
