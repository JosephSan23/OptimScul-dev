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
    List<Asignatura> findByInstitucionId(UUID institucionId);
    boolean existsByInstitucionIdAndCodigo(UUID institucionId, String codigo);
    java.util.Map<UUID, Long> contarPorAreaDeInstitucion(UUID institucionId);
}
