package backend.academic.application.usecase.grado;

import backend.academic.application.port.GradoRepository;
import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.Grado;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ListarGradosUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    public record GradoConGrupos(Grado grado, long totalGrupos) {}

    private final GradoRepository gradoRepo;
    private final GrupoRepository grupoRepo;
    private final AutorizacionService auth;

    public ListarGradosUseCase(GradoRepository gradoRepo, GrupoRepository grupoRepo, AutorizacionService auth) {
        this.gradoRepo = gradoRepo; this.grupoRepo = grupoRepo; this.auth = auth;
    }

    public List<GradoConGrupos> ejecutar(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        Map<UUID, Long> conteos = grupoRepo.contarPorGradoDeInstitucion(inst);
        return gradoRepo.findByInstitucionId(inst).stream()
                .map(g -> new GradoConGrupos(g, conteos.getOrDefault(g.getId(), 0L)))
                .toList();
    }
}