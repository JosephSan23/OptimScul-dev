package backend.academic.application.usecase.actividad;

import backend.academic.application.port.ActividadAcademicaRepository;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.EstadoActividad;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoActividadUseCase {
    private final ActividadAcademicaRepository actividadRepo;
    private final ObtenerActividadUseCase obtener;

    public CambiarEstadoActividadUseCase(ActividadAcademicaRepository actividadRepo, ObtenerActividadUseCase obtener) {
        this.actividadRepo = actividadRepo;
        this.obtener = obtener;
    }

    @Transactional
    public void publicar(UUID u, UUID id) {
        cambiar(u, id, EstadoActividad.PUBLICADA);
    }

    @Transactional
    public void cerrar(UUID u, UUID id) {
        cambiar(u, id, EstadoActividad.CERRADA);
    }

    @Transactional
    public void anular(UUID u, UUID id) {
        cambiar(u, id, EstadoActividad.ANULADA);
    }

    private void cambiar(UUID usuarioId, UUID actividadId, EstadoActividad estado) {
        ActividadAcademica a = obtener.ejecutar(usuarioId, actividadId); // valida propiedad
        a.setEstado(estado);
        a.setUpdatedBy(usuarioId);
        a.setUpdatedAt(LocalDateTime.now());
        actividadRepo.save(a);
    }
}