package backend.admission.application.port;

import backend.admission.domain.model.Postulacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostulacionRepository {

    Postulacion save(Postulacion entity);
    Optional<Postulacion> findById(UUID id);
    List<Postulacion> findAll();
    void deleteById(UUID id);
}
