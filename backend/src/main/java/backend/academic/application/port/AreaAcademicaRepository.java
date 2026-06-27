package backend.academic.application.port;

import backend.academic.domain.model.AreaAcademica;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AreaAcademicaRepository {

    AreaAcademica save(AreaAcademica entity);
    Optional<AreaAcademica> findById(UUID id);
    List<AreaAcademica> findAll();
    void deleteById(UUID id);
}
