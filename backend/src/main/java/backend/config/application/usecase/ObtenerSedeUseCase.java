package backend.config.application.usecase;

import backend.people.application.port.SedeRepository;
import backend.people.domain.model.Sede;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ObtenerSedeUseCase {
    private final SedeRepository sedeRepository;
    private final AutorizacionService autorizacionService;

    public ObtenerSedeUseCase(SedeRepository sedeRepository, AutorizacionService autorizacionService) {
        this.sedeRepository = sedeRepository;
        this.autorizacionService = autorizacionService;
    }

    public Sede ejecutar(UUID adminId, UUID sedeId) {
        UUID institucionId = autorizacionService.institucionDelAdmin(adminId);
        Sede s = sedeRepository.findById(sedeId)
                .orElseThrow(() -> new RuntimeException("La sede no existe."));
        if (!institucionId.equals(s.getInstitucionId())) {
            throw new SecurityException("No puedes ver sedes de otra institución.");
        }
        return s;
    }
}