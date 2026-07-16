package backend.academic.application.port;

import backend.academic.domain.model.Grado;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradoRepository {

    Grado save(Grado entity);
    Optional<Grado> findById(UUID id);
    List<Grado> findAll();
    void deleteById(UUID id);
    List<Grado> findByInstitucionId(UUID institucionId);
    boolean existsByInstitucionIdAndCodigo(UUID institucionId, String codigo);
}
