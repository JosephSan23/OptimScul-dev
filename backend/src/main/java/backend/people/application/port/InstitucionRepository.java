package backend.people.application.port;

import backend.people.domain.model.Institucion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstitucionRepository {

    Institucion save(Institucion entity);
    Optional<Institucion> findById(UUID id);
    List<Institucion> findAll();
    void deleteById(UUID id);
}
