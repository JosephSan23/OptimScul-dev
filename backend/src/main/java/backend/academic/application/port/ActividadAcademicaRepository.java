package backend.academic.application.port;

import backend.academic.domain.model.ActividadAcademica;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActividadAcademicaRepository {

    ActividadAcademica save(ActividadAcademica entity);
    Optional<ActividadAcademica> findById(UUID id);
    List<ActividadAcademica> findAll();
    void deleteById(UUID id);
}
