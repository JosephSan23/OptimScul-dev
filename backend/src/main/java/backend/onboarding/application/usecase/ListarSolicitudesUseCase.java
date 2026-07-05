package backend.onboarding.application.usecase;

import backend.onboarding.application.port.SolicitudInstitucionRepository;
import backend.onboarding.domain.model.SolicitudInstitucion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarSolicitudesUseCase {

    private final SolicitudInstitucionRepository solicitudRepository;

    public ListarSolicitudesUseCase(SolicitudInstitucionRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public List<SolicitudInstitucion> ejecutar() {
        return solicitudRepository.findAll();
    }
}