package backend.academic.application.usecase.grupo;

import backend.academic.application.port.GradoRepository;
import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.Grado;
import backend.academic.domain.model.Grupo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarGruposPorGradoUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    private final GrupoRepository grupoRepo;
    private final GradoRepository gradoRepo;
    private final AutorizacionService auth;

    public ListarGruposPorGradoUseCase(GrupoRepository grupoRepo, GradoRepository gradoRepo, AutorizacionService auth) {
        this.grupoRepo = grupoRepo;
        this.gradoRepo = gradoRepo;
        this.auth = auth;
    }

    public List<Grupo> ejecutar(UUID usuarioId, UUID gradoId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        Grado grado = gradoRepo.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("El grado no existe."));
        if (!inst.equals(grado.getInstitucionId()))
            throw new SecurityException("No puedes ver grupos de otra institución.");
        return grupoRepo.findByGradoId(gradoId);
    }
}