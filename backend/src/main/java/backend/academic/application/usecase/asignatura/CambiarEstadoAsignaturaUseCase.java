package backend.academic.application.usecase.asignatura;

import backend.academic.application.port.AsignaturaRepository;
import backend.academic.domain.model.Asignatura;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoAsignaturaUseCase {
    private final AsignaturaRepository repo;
    private final AutorizacionService auth;

    public CambiarEstadoAsignaturaUseCase(AsignaturaRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    @Transactional
    public void activar(UUID u, UUID id) {
        cambiar(u, id, true);
    }

    @Transactional
    public void inactivar(UUID u, UUID id) {
        cambiar(u, id, false);
    }

    private void cambiar(UUID usuarioId, UUID asignaturaId, boolean activa) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAsignaturasUseCase.ROLES);
        Asignatura a = repo.findById(asignaturaId)
                .orElseThrow(() -> new RuntimeException("La asignatura no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes cambiar asignaturas de otra institución.");
        a.setActiva(activa);
        a.setUpdatedBy(usuarioId);
        a.setUpdatedAt(LocalDateTime.now());
        repo.save(a);
    }
}