package backend.chat.application.port;

import backend.chat.domain.model.Mensaje;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MensajeRepository {
    Mensaje save(Mensaje mensaje);
    List<Mensaje> findByConversacion(UUID conversacionId);
    Optional<Mensaje> findUltimo(UUID conversacionId);
    long contarNoLeidos(UUID conversacionId, UUID paraUsuarioId);
    List<Mensaje> findUltimosDeConversaciones(List<UUID> conversacionIds);
    Map<UUID, Long> contarNoLeidosPorConversacion(List<UUID> conversacionIds, UUID paraUsuarioId);
    void marcarLeidos(UUID conversacionId, UUID paraUsuarioId);
}