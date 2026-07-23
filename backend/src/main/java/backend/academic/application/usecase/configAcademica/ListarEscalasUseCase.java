package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.EscalaValorativaRepository;
import backend.academic.domain.model.EscalaValorativa;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarEscalasUseCase {
    private final EscalaValorativaRepository repo;
    private final AutorizacionService auth;

    public ListarEscalasUseCase(EscalaValorativaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public List<EscalaValorativa> ejecutar(UUID usuarioId) {
        return repo.findByInstitucionId(auth.institucionConRol(usuarioId, CrearEscalaUseCase.ROLES));
    }
}