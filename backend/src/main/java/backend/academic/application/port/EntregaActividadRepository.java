package backend.academic.application.port;

import backend.academic.domain.model.EntregaActividad;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntregaActividadRepository {

    EntregaActividad save(EntregaActividad entity);
    Optional<EntregaActividad> findById(UUID id);
    List<EntregaActividad> findAll();
    void deleteById(UUID id);
}
