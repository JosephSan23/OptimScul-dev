package backend.academic.application.usecase.area;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.application.port.AsignaturaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ListarAreasUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    public record AreaConAsignaturas(AreaAcademica area, long totalAsignaturas) {}

    private final AreaAcademicaRepository areaRepo;
    private final AsignaturaRepository asignaturaRepo;
    private final AutorizacionService auth;

    public ListarAreasUseCase(AreaAcademicaRepository areaRepo, AsignaturaRepository asignaturaRepo, AutorizacionService auth) {
        this.areaRepo = areaRepo; this.asignaturaRepo = asignaturaRepo; this.auth = auth;
    }

    public List<AreaConAsignaturas> ejecutar(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        Map<UUID, Long> conteos = asignaturaRepo.contarPorAreaDeInstitucion(inst);
        return areaRepo.findByInstitucionId(inst).stream()
                .map(a -> new AreaConAsignaturas(a, conteos.getOrDefault(a.getId(), 0L)))
                .toList();
    }
} 
