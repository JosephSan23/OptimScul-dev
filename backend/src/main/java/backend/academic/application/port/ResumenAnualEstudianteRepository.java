package backend.academic.application.port;

import backend.academic.domain.model.ResumenAnualEstudiante;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumenAnualEstudianteRepository {

    ResumenAnualEstudiante save(ResumenAnualEstudiante entity);
    Optional<ResumenAnualEstudiante> findById(UUID id);
    List<ResumenAnualEstudiante> findAll();
    void deleteById(UUID id);
}
