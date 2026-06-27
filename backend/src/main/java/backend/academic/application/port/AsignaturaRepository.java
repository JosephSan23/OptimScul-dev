package backend.academic.application.port;

import backend.academic.domain.model.Asignatura;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AsignaturaRepository {

    Asignatura save(Asignatura entity);
    Optional<Asignatura> findById(UUID id);
    List<Asignatura> findAll();
    void deleteById(UUID id);
}
