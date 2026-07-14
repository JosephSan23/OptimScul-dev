package backend.config.application.usecase.jornada;

import backend.people.application.port.JornadaRepository;
import backend.people.domain.model.Jornada;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerJornadaUseCase {
    private final JornadaRepository jornadaRepository;
    private final AutorizacionService autorizacionService;
    public ObtenerJornadaUseCase(JornadaRepository jornadaRepository, AutorizacionService autorizacionService) {
        this.jornadaRepository = jornadaRepository; this.autorizacionService = autorizacionService;
    }
    public Jornada ejecutar(UUID adminId, UUID jornadaId) {
        UUID inst = autorizacionService.institucionDelAdmin(adminId);
        Jornada j = jornadaRepository.findById(jornadaId)
                .orElseThrow(() -> new RuntimeException("La jornada no existe."));
        if (!inst.equals(j.getInstitucionId())) throw new SecurityException("No puedes ver jornadas de otra institución.");
        return j;
    }
}