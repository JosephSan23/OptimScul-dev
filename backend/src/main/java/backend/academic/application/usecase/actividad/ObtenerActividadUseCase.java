package backend.academic.application.usecase.actividad;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.CargaAcademica;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ObtenerActividadUseCase {
    private final ActividadAcademicaRepository actividadRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final ContextoDocenteService contexto;

    public ObtenerActividadUseCase(ActividadAcademicaRepository actividadRepo, CargaAcademicaRepository cargaRepo,
            ContextoDocenteService contexto) {
        this.actividadRepo = actividadRepo;
        this.cargaRepo = cargaRepo;
        this.contexto = contexto;
    }

    /**
     * Devuelve la actividad garantizando que pertenece a una clase del profesor
     * logueado.
     */
    public ActividadAcademica ejecutar(UUID usuarioId, UUID actividadId) {
        var ctx = contexto.resolver(usuarioId);
        ActividadAcademica a = actividadRepo.findById(actividadId)
                .orElseThrow(() -> new RuntimeException("La actividad no existe."));
        CargaAcademica carga = cargaRepo.findById(a.getCargaAcademicaId())
                .orElseThrow(() -> new RuntimeException("La clase de la actividad no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa actividad no te pertenece.");
        return a;
    }
}