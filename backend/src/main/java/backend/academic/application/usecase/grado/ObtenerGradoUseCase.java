package backend.academic.application.usecase.grado;

import backend.academic.application.port.GradoRepository;
import backend.academic.domain.model.Grado;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerGradoUseCase {
    private final GradoRepository repo;
    private final AutorizacionService auth;

    public ObtenerGradoUseCase(GradoRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public Grado ejecutar(UUID usuarioId, UUID gradoId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGradosUseCase.ROLES);
        Grado g = repo.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("El grado no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("No puedes ver grados de otra institución.");
        return g;
    }
}