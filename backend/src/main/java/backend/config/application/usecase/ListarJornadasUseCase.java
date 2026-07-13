package backend.config.application.usecase;

import backend.people.application.port.JornadaRepository;
import backend.people.domain.model.Jornada;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarJornadasUseCase {
    private final JornadaRepository jornadaRepository;
    private final AutorizacionService autorizacionService;
    public ListarJornadasUseCase(JornadaRepository jornadaRepository, AutorizacionService autorizacionService) {
        this.jornadaRepository = jornadaRepository; this.autorizacionService = autorizacionService;
    }
    public List<Jornada> ejecutar(UUID adminId) {
        return jornadaRepository.findByInstitucionId(autorizacionService.institucionDelAdmin(adminId));
    }
}