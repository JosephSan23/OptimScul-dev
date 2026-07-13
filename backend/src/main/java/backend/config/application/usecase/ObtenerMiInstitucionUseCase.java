package backend.config.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerMiInstitucionUseCase {
    private final InstitucionRepository institucionRepository;
    private final AutorizacionService autorizacionService;
    public ObtenerMiInstitucionUseCase(InstitucionRepository institucionRepository, AutorizacionService autorizacionService) {
        this.institucionRepository = institucionRepository; this.autorizacionService = autorizacionService;
    }
    public Institucion ejecutar(UUID adminId) {
        UUID inst = autorizacionService.institucionDelAdmin(adminId);
        return institucionRepository.findById(inst)
                .orElseThrow(() -> new RuntimeException("Institución no encontrada."));
    }
}