package backend.academic.application.port;

import backend.academic.domain.model.ConfiguracionAcademica;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfiguracionAcademicaRepository {

    ConfiguracionAcademica save(ConfiguracionAcademica entity);
    Optional<ConfiguracionAcademica> findById(UUID id);
    List<ConfiguracionAcademica> findAll();
    void deleteById(UUID id);
    Optional<ConfiguracionAcademica> findByInstitucionId(UUID institucionId);
}
