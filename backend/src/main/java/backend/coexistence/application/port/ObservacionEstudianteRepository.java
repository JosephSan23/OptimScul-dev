package backend.coexistence.application.port;

import backend.coexistence.domain.model.ObservacionEstudiante;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ObservacionEstudianteRepository {

    ObservacionEstudiante save(ObservacionEstudiante entity);
    Optional<ObservacionEstudiante> findById(UUID id);
    List<ObservacionEstudiante> findAll();
    void deleteById(UUID id);
}
