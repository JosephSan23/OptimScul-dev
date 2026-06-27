package backend.people.application.port;

import backend.people.domain.model.EstudianteAcudiente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteAcudienteRepository {

    EstudianteAcudiente save(EstudianteAcudiente entity);
    Optional<EstudianteAcudiente> findById(UUID id);
    List<EstudianteAcudiente> findAll();
    void deleteById(UUID id);
}
