package backend.chat.application.port;

import backend.chat.domain.model.Conversacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversacionRepository {
    Conversacion save(Conversacion conversacion);
    Optional<Conversacion> findById(UUID id);
    Optional<Conversacion> findByPar(UUID institucionId, UUID usuarioMenorId, UUID usuarioMayorId);
    List<Conversacion> findAllDeUsuario(UUID usuarioId);
}