package backend.config.application.usecase.sede;

import backend.people.application.port.SedeRepository;
import backend.people.domain.model.Sede;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarSedesUseCase {
    private final SedeRepository sedeRepository;
    private final AutorizacionService autorizacionService;

    public ListarSedesUseCase(SedeRepository sedeRepository, AutorizacionService autorizacionService) {
        this.sedeRepository = sedeRepository;
        this.autorizacionService = autorizacionService;
    }

    public List<Sede> ejecutar(UUID adminId) {
        UUID institucionId = autorizacionService.institucionConRol(adminId, "ADMIN_INSTITUCION", "COORDINADOR_ACADEMICO");
        return sedeRepository.findByInstitucionId(institucionId);
    }
}