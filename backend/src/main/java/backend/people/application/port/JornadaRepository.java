package backend.people.application.port;

import backend.people.domain.model.Jornada;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JornadaRepository {

    Jornada save(Jornada entity);
    Optional<Jornada> findById(UUID id);
    List<Jornada> findAll();
    void deleteById(UUID id);
    List<Jornada> findByInstitucionId(UUID institucionId);
}
