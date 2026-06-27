package backend.academic.application.port;

import backend.academic.domain.model.Grupo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GrupoRepository {

    Grupo save(Grupo entity);
    Optional<Grupo> findById(UUID id);
    List<Grupo> findAll();
    void deleteById(UUID id);
}
