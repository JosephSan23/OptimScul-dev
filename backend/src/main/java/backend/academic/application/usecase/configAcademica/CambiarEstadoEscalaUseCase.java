package backend.academic.application.usecase.configAcademica;

import backend.academic.application.port.EscalaValorativaRepository;
import backend.academic.domain.model.EscalaValorativa;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoEscalaUseCase {
    private final EscalaValorativaRepository repo;
    private final AutorizacionService auth;

    public CambiarEstadoEscalaUseCase(EscalaValorativaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional public void activar(UUID u, UUID id)   { cambiar(u, id, true); }
    @Transactional public void inactivar(UUID u, UUID id) { cambiar(u, id, false); }

    private void cambiar(UUID usuarioId, UUID escalaId, boolean activa) {
        UUID inst = auth.institucionConRol(usuarioId, CrearEscalaUseCase.ROLES);
        EscalaValorativa e = repo.findById(escalaId)
                .orElseThrow(() -> new RuntimeException("La escala no existe."));
        if (!inst.equals(e.getInstitucionId()))
            throw new SecurityException("No puedes cambiar escalas de otra institución.");
        e.setActiva(activa);
        e.setUpdatedAt(LocalDateTime.now());
        repo.save(e);
    }
}