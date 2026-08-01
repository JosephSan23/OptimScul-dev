package backend.chat.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DirectorioUsuarioPort {

    record Contacto(UUID usuarioId, String nombre, String relacion) {}

    Optional<UUID> institucionActiva(UUID usuarioId);

    String nombreCompleto(UUID usuarioId);

    List<Contacto> contactos(UUID usuarioId, UUID institucionId);

    boolean puedenChatear(UUID usuarioA, UUID usuarioB, UUID institucionId);
}