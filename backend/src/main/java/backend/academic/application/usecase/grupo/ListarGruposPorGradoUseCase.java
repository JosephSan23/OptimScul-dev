package backend.academic.application.usecase.grupo;

import backend.academic.application.port.GradoRepository;
import backend.academic.application.port.GrupoRepository;
import backend.enrollment.application.port.MatriculaRepository;
import backend.academic.domain.model.Grado;
import backend.academic.domain.model.Grupo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Service
public class ListarGruposPorGradoUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final GrupoRepository grupoRepo;
    private final GradoRepository gradoRepo;
    private final AutorizacionService auth;
    private final MatriculaRepository matriculaRepo;
    public ListarGruposPorGradoUseCase(GrupoRepository grupoRepo, GradoRepository gradoRepo, AutorizacionService auth, MatriculaRepository matriculaRepo) {
        this.grupoRepo = grupoRepo;
        this.gradoRepo = gradoRepo;
        this.auth = auth;
        this.matriculaRepo = matriculaRepo;
    }

    public record GrupoConConteo(Grupo grupo, long totalEstudiantes) {}

    public List<GrupoConConteo> ejecutar(UUID usuarioId, UUID gradoId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        Grado grado = gradoRepo.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("El grado no existe."));
        if (!inst.equals(grado.getInstitucionId()))
            throw new SecurityException("No puedes ver grupos de otra institución.");
        Map<UUID, Long> conteos = matriculaRepo.contarMatriculadosPorGrupoDeInstitucion(inst);
        return grupoRepo.findByGradoId(gradoId).stream()
                .map(g -> new GrupoConConteo(g, conteos.getOrDefault(g.getId(), 0L)))
                .toList();
    }

    
}