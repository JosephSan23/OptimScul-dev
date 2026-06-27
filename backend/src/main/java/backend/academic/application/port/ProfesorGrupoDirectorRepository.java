package backend.academic.application.port;

import backend.academic.domain.model.ProfesorGrupoDirector;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfesorGrupoDirectorRepository {

    ProfesorGrupoDirector save(ProfesorGrupoDirector entity);
    Optional<ProfesorGrupoDirector> findById(UUID id);
    List<ProfesorGrupoDirector> findAll();
    void deleteById(UUID id);
}
