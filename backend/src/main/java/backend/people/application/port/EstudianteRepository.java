package backend.people.application.port;

import backend.people.domain.model.Estudiante;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteRepository {

    Estudiante save(Estudiante entity);
    Optional<Estudiante> findById(UUID id);
    List<Estudiante> findAll();
    void deleteById(UUID id);
    List<Estudiante> findByInstitucionId(UUID institucionId);
    Optional<Estudiante> findByInstitucionIdAndPersonaId(UUID institucionId, UUID personaId);
}
