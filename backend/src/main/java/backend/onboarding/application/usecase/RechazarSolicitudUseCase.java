package backend.onboarding.application.usecase;

import backend.onboarding.application.port.SolicitudInstitucionRepository;
import backend.onboarding.domain.model.SolicitudInstitucion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RechazarSolicitudUseCase {

    private final SolicitudInstitucionRepository solicitudRepository;

    public RechazarSolicitudUseCase(SolicitudInstitucionRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public SolicitudInstitucion ejecutar(UUID solicitudId, String motivo, UUID rechazadaPorUsuarioId) {

        SolicitudInstitucion solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("No existe una solicitud con el id " + solicitudId));

        if (!"PENDIENTE".equals(solicitud.getEstado())) {
            throw new RuntimeException("Esta solicitud ya fue procesada (estado: " + solicitud.getEstado() + ").");
        }

        solicitud.setEstado("RECHAZADA");
        solicitud.setRevisadaPorUsuarioId(rechazadaPorUsuarioId);
        solicitud.setFechaRevision(LocalDateTime.now());
        solicitud.setMotivoRechazo(motivo);
        solicitud.setUpdatedAt(LocalDateTime.now());

        return solicitudRepository.save(solicitud);
    }
}