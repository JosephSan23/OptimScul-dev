package backend.onboarding.application.port;

import backend.onboarding.domain.model.SolicitudInstitucion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitudInstitucionRepository {
    SolicitudInstitucion save(SolicitudInstitucion solicitud);
    Optional<SolicitudInstitucion> findById(UUID id);
    List<SolicitudInstitucion> findAll();
    boolean existsByEnviadaPorUsuarioIdAndEstado(UUID usuarioId, String estado);
    List<SolicitudInstitucion> findByEnviadaPorUsuarioId(UUID usuarioId);
}