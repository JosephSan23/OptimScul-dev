package backend.shared.application.port;

import backend.shared.ModuloDocumento;
import backend.shared.domain.model.Documento;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentoRepository {

    Documento save(Documento entity);
    Optional<Documento> findById(UUID id);
    List<Documento> findAll();
    void deleteById(UUID id);

    List<Documento> findByModuloAndEntidadId(ModuloDocumento modulo, UUID entidadId);

}
