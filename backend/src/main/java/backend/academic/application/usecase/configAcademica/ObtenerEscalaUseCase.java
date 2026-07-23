package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.EscalaValorativaRepository;
import backend.academic.domain.model.EscalaValorativa;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerEscalaUseCase {
    private final EscalaValorativaRepository repo;
    private final AutorizacionService auth;

    public ObtenerEscalaUseCase(EscalaValorativaRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public EscalaValorativa ejecutar(UUID usuarioId, UUID escalaId) {
        UUID inst = auth.institucionConRol(usuarioId, CrearEscalaUseCase.ROLES);
        EscalaValorativa e = repo.findById(escalaId)
                .orElseThrow(() -> new RuntimeException("La escala no existe."));
        if (!inst.equals(e.getInstitucionId()))
            throw new SecurityException("No puedes ver escalas de otra institución.");
        return e;
    }
}
