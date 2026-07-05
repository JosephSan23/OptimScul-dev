package backend.onboarding.application.usecase;

import backend.onboarding.application.port.SolicitudInstitucionRepository;
import backend.onboarding.domain.model.SolicitudInstitucion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecibirSolicitudUseCase {

    private final SolicitudInstitucionRepository solicitudRepository;

    public RecibirSolicitudUseCase(SolicitudInstitucionRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public SolicitudInstitucion ejecutar(SolicitudInstitucion solicitud, UUID usuarioId) {

        // Regla de negocio: un usuario no puede tener dos solicitudes pendientes a la vez
        if (solicitudRepository.existsByEnviadaPorUsuarioIdAndEstado(usuarioId, "PENDIENTE")) {
            throw new RuntimeException("Ya tienes una solicitud pendiente. Espera a que sea revisada.");
        }

        // El sistema asigna estos valores
        solicitud.setId(UUID.randomUUID());
        solicitud.setEnviadaPorUsuarioId(usuarioId);
        solicitud.setEstado("PENDIENTE");

        LocalDateTime ahora = LocalDateTime.now();
        solicitud.setCreatedAt(ahora);
        solicitud.setUpdatedAt(ahora);

        return solicitudRepository.save(solicitud);
    }
}