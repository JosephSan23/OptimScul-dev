package backend.security.application.port;

import backend.security.domain.model.UsuarioRol;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRolRepository {

    UsuarioRol save(UsuarioRol entity);
    Optional<UsuarioRol> findById(UUID id);
    List<UsuarioRol> findAll();
    void deleteById(UUID id);
}
