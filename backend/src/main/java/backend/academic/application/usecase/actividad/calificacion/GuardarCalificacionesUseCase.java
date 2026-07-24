package backend.academic.application.usecase.actividad.calificacion;

import backend.academic.application.port.CalificacionActividadRepository;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.EstudianteDeClase;
import backend.academic.application.usecase.actividad.ObtenerActividadUseCase;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.CalificacionActividad;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.EstadoActividad;
import backend.academic.infrastructure.rest.dto.Calificacion.GuardarCalificacionesRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GuardarCalificacionesUseCase {

    private final ObtenerActividadUseCase obtenerActividad;
    private final CargaAcademicaRepository cargaRepo;
    private final DocenteConsultaRepository docenteConsulta;
    private final CalificacionActividadRepository calificacionRepo;

    public GuardarCalificacionesUseCase(ObtenerActividadUseCase obtenerActividad, CargaAcademicaRepository cargaRepo,
            DocenteConsultaRepository docenteConsulta,
            CalificacionActividadRepository calificacionRepo) {
        this.obtenerActividad = obtenerActividad;
        this.cargaRepo = cargaRepo;
        this.docenteConsulta = docenteConsulta;
        this.calificacionRepo = calificacionRepo;
    }

    @Transactional
    public void ejecutar(UUID usuarioId, UUID actividadId, GuardarCalificacionesRequestDto dto) {
        ActividadAcademica act = obtenerActividad.ejecutar(usuarioId, actividadId); // valida propiedad
        if (act.getEstado() == EstadoActividad.ANULADA)
            throw new RuntimeException("No se puede calificar una actividad anulada.");

        CargaAcademica carga = cargaRepo.findById(act.getCargaAcademicaId())
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        Set<UUID> validos = docenteConsulta.estudiantesDeGrupo(carga.getGrupoId()).stream()
                .map(EstudianteDeClase::getEstudianteId).collect(Collectors.toSet());

        BigDecimal max = act.getNotaMaxima();
        LocalDateTime ahora = LocalDateTime.now();

        for (GuardarCalificacionesRequestDto.Nota n : dto.getNotas()) {
            if (!validos.contains(n.getEstudianteId()))
                continue; // ignora ajenos al grupo

            if (n.getNotaObtenida() != null) {
                if (n.getNotaObtenida().signum() < 0 || n.getNotaObtenida().compareTo(max) > 0)
                    throw new RuntimeException("Las notas deben estar entre 0 y " + max + ".");
            }

            CalificacionActividad c = calificacionRepo
                    .findByActividadIdAndEstudianteId(actividadId, n.getEstudianteId())
                    .orElseGet(() -> {
                        CalificacionActividad nueva = new CalificacionActividad();
                        nueva.setId(UUID.randomUUID());
                        nueva.setActividadId(actividadId);
                        nueva.setEstudianteId(n.getEstudianteId());
                        nueva.setAnulada(false);
                        nueva.setCreatedAt(ahora);
                        return nueva;
                    });
            c.setNotaObtenida(n.getNotaObtenida());
            c.setObservacionDocente(n.getObservacion());
            c.setEsRecuperacion(n.getEsRecuperacion() != null ? n.getEsRecuperacion() : false);
            c.setCalificadaPorUsuarioId(usuarioId);
            c.setFechaCalificacion(ahora);
            c.setUpdatedAt(ahora);
            calificacionRepo.save(c);
        }
    }
}