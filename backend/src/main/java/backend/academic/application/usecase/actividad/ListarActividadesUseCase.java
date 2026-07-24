package backend.academic.application.usecase.actividad;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.CargaAcademica;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarActividadesUseCase {
    private final ActividadAcademicaRepository actividadRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final ContextoDocenteService contexto;

    public ListarActividadesUseCase(ActividadAcademicaRepository actividadRepo, CargaAcademicaRepository cargaRepo,
                                    ContextoDocenteService contexto) {
        this.actividadRepo = actividadRepo; this.cargaRepo = cargaRepo; this.contexto = contexto;
    }

    public List<ActividadAcademica> ejecutar(UUID usuarioId, UUID cargaId, UUID periodoId) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");
        return actividadRepo.findByCargaYPeriodo(cargaId, periodoId);
    }
}