package backend.academic.application.usecase.grupo;

import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.Grupo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerGrupoUseCase {
    private final GrupoRepository repo;
    private final AutorizacionService auth;

    public ObtenerGrupoUseCase(GrupoRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    public Grupo ejecutar(UUID usuarioId, UUID grupoId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGruposPorGradoUseCase.ROLES);
        Grupo g = repo.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("El grupo no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("No puedes ver grupos de otra institución.");
        return g;
    }
}