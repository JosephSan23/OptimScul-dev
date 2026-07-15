package backend.people.application.port;

import backend.people.domain.model.Acudiente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AcudienteRepository {

    Acudiente save(Acudiente entity);
    Optional<Acudiente> findById(UUID id);
    List<Acudiente> findAll();
    void deleteById(UUID id);
    Optional<Acudiente> findByPersonaId(UUID personaId);
}
