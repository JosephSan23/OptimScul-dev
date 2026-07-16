package backend.academic.application.usecase.grado;

import backend.academic.application.port.GradoRepository;
import backend.academic.domain.model.Grado;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarGradosUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final GradoRepository repo;
    private final AutorizacionService auth;

    public ListarGradosUseCase(GradoRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public List<Grado> ejecutar(UUID usuarioId) {
        return repo.findByInstitucionId(auth.institucionConRol(usuarioId, ROLES));
    }
}