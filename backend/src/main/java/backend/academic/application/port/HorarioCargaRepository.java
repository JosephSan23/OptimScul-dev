package backend.academic.application.port;

import backend.academic.domain.model.HorarioCarga;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HorarioCargaRepository {

    HorarioCarga save(HorarioCarga entity);
    Optional<HorarioCarga> findById(UUID id);
    List<HorarioCarga> findAll();
    void deleteById(UUID id);
}
