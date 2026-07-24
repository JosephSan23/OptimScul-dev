package backend.academic.application.usecase.actividad;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.application.port.CalificacionActividadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class EliminarActividadUseCase {
    private final ActividadAcademicaRepository actividadRepo;
    private final CalificacionActividadRepository calificacionRepo;
    private final ObtenerActividadUseCase obtener;

    public EliminarActividadUseCase(ActividadAcademicaRepository actividadRepo,
            CalificacionActividadRepository calificacionRepo,
            ObtenerActividadUseCase obtener) {
        this.actividadRepo = actividadRepo;
        this.calificacionRepo = calificacionRepo;
        this.obtener = obtener;
    }

    @Transactional
    public void ejecutar(UUID usuarioId, UUID actividadId) {
        obtener.ejecutar(usuarioId, actividadId); // valida propiedad
        if (calificacionRepo.existsByActividadId(actividadId))
            throw new RuntimeException("No se puede eliminar: la actividad ya tiene notas. Anúlala en su lugar.");
        actividadRepo.deleteById(actividadId);
    }
}