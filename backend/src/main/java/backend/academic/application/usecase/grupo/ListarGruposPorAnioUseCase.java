package backend.academic.application.usecase.grupo;

import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.Grupo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarGruposPorAnioUseCase {
    private final GrupoRepository repo;
    private final AutorizacionService auth;

    public ListarGruposPorAnioUseCase(GrupoRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    public List<Grupo> ejecutar(UUID usuarioId, UUID anioId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGruposPorGradoUseCase.ROLES);
        return repo.findByInstitucionIdAndAnioLectivoId(inst, anioId);
    }
}