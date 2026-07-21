package backend.academic.application.port;

import backend.academic.domain.model.AsistenciaClase;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AsistenciaClaseRepository {

    AsistenciaClase save(AsistenciaClase entity);
    Optional<AsistenciaClase> findById(UUID id);
    List<AsistenciaClase> findAll();
    void deleteById(UUID id);
    List<AsistenciaClase> findBySesionClaseId(UUID sesionClaseId);
    Optional<AsistenciaClase> findBySesionClaseIdAndEstudianteId(UUID sesionClaseId, UUID estudianteId);
}
