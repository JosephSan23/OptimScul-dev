package backend.config.application.usecase.periodoAcademico;

import backend.academic.application.port.PeriodoAcademicoRepository;
import backend.academic.domain.model.PeriodoAcademico;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerPeriodoUseCase {
    private final PeriodoAcademicoRepository periodoRepo;
    private final AutorizacionService auth;
    public ObtenerPeriodoUseCase(PeriodoAcademicoRepository periodoRepo, AutorizacionService auth) {
        this.periodoRepo = periodoRepo; this.auth = auth;
    }
    public PeriodoAcademico ejecutar(UUID adminId, UUID periodoId) {
        UUID inst = auth.institucionDelAdmin(adminId);
        PeriodoAcademico p = periodoRepo.findById(periodoId).orElseThrow(() -> new RuntimeException("El periodo no existe."));
        if (!inst.equals(p.getInstitucionId())) throw new SecurityException("No puedes ver periodos de otra institución.");
        return p;
    }
}