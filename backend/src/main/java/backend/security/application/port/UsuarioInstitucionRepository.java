package backend.security.application.port;

import backend.security.domain.model.UsuarioInstitucion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioInstitucionRepository {

    UsuarioInstitucion save(UsuarioInstitucion entity);
    Optional<UsuarioInstitucion> findById(UUID id);
    List<UsuarioInstitucion> findAll();
    void deleteById(UUID id);
    List<UsuarioInstitucion> findByUsuarioId(UUID usuarioId);
    List<UsuarioInstitucion> findByInstitucionId(UUID institucionId);
}
