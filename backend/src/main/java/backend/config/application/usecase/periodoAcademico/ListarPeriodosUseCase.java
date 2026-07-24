package backend.config.application.usecase.periodoAcademico;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.application.port.PeriodoAcademicoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.domain.model.PeriodoAcademico;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarPeriodosUseCase {
    private final PeriodoAcademicoRepository periodoRepo;
    private final AnioLectivoRepository anioRepo;
    private final AutorizacionService auth;
    public ListarPeriodosUseCase(PeriodoAcademicoRepository periodoRepo, AnioLectivoRepository anioRepo, AutorizacionService auth) {
        this.periodoRepo = periodoRepo; this.anioRepo = anioRepo; this.auth = auth;
    }
    public List<PeriodoAcademico> ejecutar(UUID adminId, UUID anioId) {
        UUID inst = auth.institucionConRol(adminId, "ADMIN_INSTITUCION", "COORDINADOR_ACADEMICO", "DOCENTE", "ESTUDIANTE");
        AnioLectivo anio = anioRepo.findById(anioId).orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(anio.getInstitucionId())) throw new SecurityException("No puedes ver periodos de otra institución.");
        return periodoRepo.findByAnioLectivoId(anioId);
    }
}