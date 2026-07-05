package backend.onboarding.application.usecase;

import backend.onboarding.application.port.SolicitudInstitucionRepository;
import backend.onboarding.domain.model.SolicitudInstitucion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarMisSolicitudesUseCase {

    private final SolicitudInstitucionRepository solicitudRepository;

    public ListarMisSolicitudesUseCase(SolicitudInstitucionRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public List<SolicitudInstitucion> ejecutar(UUID usuarioId) {
        return solicitudRepository.findByEnviadaPorUsuarioId(usuarioId);
    }
}