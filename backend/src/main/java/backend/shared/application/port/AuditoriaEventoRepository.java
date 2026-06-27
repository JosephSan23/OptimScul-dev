package backend.shared.application.port;

import backend.shared.domain.model.AuditoriaEvento;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuditoriaEventoRepository {

    AuditoriaEvento save(AuditoriaEvento entity);
    Optional<AuditoriaEvento> findById(UUID id);
    List<AuditoriaEvento> findAll();
    void deleteById(UUID id);
}
