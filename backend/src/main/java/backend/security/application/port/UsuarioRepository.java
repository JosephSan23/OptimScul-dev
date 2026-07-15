package backend.security.application.port;

import backend.security.domain.model.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Usuario save(Usuario entity);
    Optional<Usuario> findById(UUID id);
    List<Usuario> findAll();
    void deleteById(UUID id);
    Optional<Usuario> findByUsernameOrEmail(String usernameOrEmail);
    List<String> findRolesByUsuarioId(UUID usuarioId);
    List<String> findRolesByUsuarioIdAndInstitucion(UUID usuarioId, UUID institucionId);
    Optional<Usuario> findByPersonaId(UUID personaId);
}
