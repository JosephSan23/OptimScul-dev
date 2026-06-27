package backend.people.application.port;

import backend.people.domain.model.Sede;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SedeRepository {

    Sede save(Sede entity);
    Optional<Sede> findById(UUID id);
    List<Sede> findAll();
    void deleteById(UUID id);
}
