package backend.academic.application.usecase.asignatura;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.application.port.AsignaturaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.academic.domain.model.Asignatura;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListarAsignaturasUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO", "ADMIN_INSTITUCION" };

    public record AsignaturaConArea(Asignatura asignatura, String areaNombre) {}

    private final AsignaturaRepository asignaturaRepo;
    private final AreaAcademicaRepository areaRepo;
    private final AutorizacionService auth;

    public ListarAsignaturasUseCase(AsignaturaRepository asignaturaRepo, AreaAcademicaRepository areaRepo,
                                    AutorizacionService auth) {
        this.asignaturaRepo = asignaturaRepo; this.areaRepo = areaRepo; this.auth = auth;
    }

    public List<AsignaturaConArea> ejecutar(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);
        Map<UUID, String> nombresArea = areaRepo.findByInstitucionId(inst).stream()
                .collect(Collectors.toMap(AreaAcademica::getId, AreaAcademica::getNombre));
        return asignaturaRepo.findByInstitucionId(inst).stream()
                .map(a -> new AsignaturaConArea(a, a.getAreaId() != null ? nombresArea.get(a.getAreaId()) : null))
                .toList();
    }
}