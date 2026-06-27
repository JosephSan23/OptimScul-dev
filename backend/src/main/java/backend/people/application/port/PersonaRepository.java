package backend.people.application.port;

import backend.people.domain.model.Persona;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonaRepository {

    Persona save(Persona entity);
    Optional<Persona> findById(UUID id);
    List<Persona> findAll();
    void deleteById(UUID id);
}
