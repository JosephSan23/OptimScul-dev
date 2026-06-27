package backend.people.application.port;

import backend.people.domain.model.Profesor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfesorRepository {

    Profesor save(Profesor entity);
    Optional<Profesor> findById(UUID id);
    List<Profesor> findAll();
    void deleteById(UUID id);
}
