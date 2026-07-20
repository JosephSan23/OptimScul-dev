package backend.enrollment.application.usecase;

import backend.enrollment.application.port.MatriculaRepository;
import backend.enrollment.domain.model.Matricula;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerMatriculaUseCase {
    private final MatriculaRepository repo;
    private final AutorizacionService auth;

    public ObtenerMatriculaUseCase(MatriculaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public Matricula ejecutar(UUID usuarioId, UUID matriculaId) {
        UUID inst = auth.institucionConRol(usuarioId, CrearMatriculaUseCase.ROLES);
        Matricula m = repo.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("La matrícula no existe."));
        if (!inst.equals(m.getInstitucionId()))
            throw new SecurityException("No puedes ver matrículas de otra institución.");
        return m;
    }
}