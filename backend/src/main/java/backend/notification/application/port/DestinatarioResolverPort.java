package backend.notification.application.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DestinatarioResolverPort {
    List<UUID> usuariosDeEstudianteYAcudientes(UUID estudianteId);
    Optional<String> emailDe(UUID usuarioId);
    List<UUID> usuariosDeGrupo(UUID grupoId);
    List<UUID> usuariosDeInstitucionAnio(UUID institucionId, UUID anioLectivoId);
}