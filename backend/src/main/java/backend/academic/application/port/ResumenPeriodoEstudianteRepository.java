package backend.academic.application.port;

import backend.academic.domain.model.ResumenPeriodoEstudiante;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumenPeriodoEstudianteRepository {

    ResumenPeriodoEstudiante save(ResumenPeriodoEstudiante entity);
    Optional<ResumenPeriodoEstudiante> findById(UUID id);
    List<ResumenPeriodoEstudiante> findAll();
    void deleteById(UUID id);
}
