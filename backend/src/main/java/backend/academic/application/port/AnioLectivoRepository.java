package backend.academic.application.port;

import backend.academic.domain.model.AnioLectivo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnioLectivoRepository {

    AnioLectivo save(AnioLectivo entity);
    Optional<AnioLectivo> findById(UUID id);
    List<AnioLectivo> findAll();
    void deleteById(UUID id);
    List<AnioLectivo> findByInstitucionId(UUID institucionId);
}
