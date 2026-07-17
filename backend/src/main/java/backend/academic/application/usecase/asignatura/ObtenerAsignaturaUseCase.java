package backend.academic.application.usecase.asignatura;

import backend.academic.application.port.AsignaturaRepository;
import backend.academic.domain.model.Asignatura;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerAsignaturaUseCase {
    private final AsignaturaRepository repo;
    private final AutorizacionService auth;

    public ObtenerAsignaturaUseCase(AsignaturaRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public Asignatura ejecutar(UUID usuarioId, UUID asignaturaId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAsignaturasUseCase.ROLES);
        Asignatura a = repo.findById(asignaturaId)
                .orElseThrow(() -> new RuntimeException("La asignatura no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes ver asignaturas de otra institución.");
        return a;
    }
}