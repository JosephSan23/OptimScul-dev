package backend.academic.application.usecase.actividad.calificacion;

import backend.academic.application.port.CalificacionActividadRepository;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.usecase.actividad.ObtenerActividadUseCase;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.CalificacionActividad;
import backend.academic.domain.model.CargaAcademica;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ObtenerCalificacionesUseCase {

        public record NotaEstudiante(UUID estudianteId, String codigoEstudiante, String nombre,
                        BigDecimal notaObtenida, String observacion) {
        }

        public record Vista(UUID actividadId, String titulo, BigDecimal notaMaxima, String estado,
                        List<NotaEstudiante> estudiantes) {
        }

        private final ObtenerActividadUseCase obtenerActividad;
        private final CargaAcademicaRepository cargaRepo;
        private final DocenteConsultaRepository docenteConsulta;
        private final CalificacionActividadRepository calificacionRepo;

        public ObtenerCalificacionesUseCase(ObtenerActividadUseCase obtenerActividad,
                        CargaAcademicaRepository cargaRepo,
                        DocenteConsultaRepository docenteConsulta,
                        CalificacionActividadRepository calificacionRepo) {
                this.obtenerActividad = obtenerActividad;
                this.cargaRepo = cargaRepo;
                this.docenteConsulta = docenteConsulta;
                this.calificacionRepo = calificacionRepo;
        }

        public Vista ejecutar(UUID usuarioId, UUID actividadId) {
                ActividadAcademica act = obtenerActividad.ejecutar(usuarioId, actividadId); // valida propiedad
                CargaAcademica carga = cargaRepo.findById(act.getCargaAcademicaId())
                                .orElseThrow(() -> new RuntimeException("La clase de la actividad no existe."));

                Map<UUID, CalificacionActividad> porEstudiante = calificacionRepo.findByActividadId(actividadId)
                                .stream()
                                .collect(Collectors.toMap(CalificacionActividad::getEstudianteId, c -> c, (a, b) -> a));

                List<NotaEstudiante> filas = docenteConsulta.estudiantesDeGrupo(carga.getGrupoId()).stream()
                                .map(e -> {
                                        CalificacionActividad c = porEstudiante.get(e.getEstudianteId());
                                        return new NotaEstudiante(e.getEstudianteId(), e.getCodigoEstudiante(),
                                                        e.getNombre(),
                                                        c != null ? c.getNotaObtenida() : null,
                                                        c != null ? c.getObservacionDocente() : null);
                                }).toList();

                return new Vista(act.getId(), act.getTitulo(), act.getNotaMaxima(),
                                act.getEstado() != null ? act.getEstado().name() : null, filas);
        }
}