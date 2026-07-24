package backend.academic.application.usecase.actividad.calificacion;

import backend.academic.application.port.*;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsolidadoPeriodoUseCase {

    public record Fila(
            UUID estudianteId,
            String codigoEstudiante,
            String nombre,
            BigDecimal notaFinal,
            boolean aprueba,
            int calificadas,
            int totalActividades
    ) {
    }

    public record Vista(
            BigDecimal sumaPorcentajes,
            BigDecimal notaAprobacion,
            List<Fila> estudiantes
    ) {
    }

    private final ContextoDocenteService contexto;
    private final CargaAcademicaRepository cargaRepo;
    private final ActividadAcademicaRepository actividadRepo;
    private final CalificacionActividadRepository calificacionRepo;
    private final DocenteConsultaRepository docenteConsulta;
    private final ConfiguracionAcademicaRepository configRepo;
    private final NotaFinalCalculator calculator;

    public ConsolidadoPeriodoUseCase(
            ContextoDocenteService contexto,
            CargaAcademicaRepository cargaRepo,
            ActividadAcademicaRepository actividadRepo,
            CalificacionActividadRepository calificacionRepo,
            DocenteConsultaRepository docenteConsulta,
            ConfiguracionAcademicaRepository configRepo,
            NotaFinalCalculator calculator) {

        this.contexto = contexto;
        this.cargaRepo = cargaRepo;
        this.actividadRepo = actividadRepo;
        this.calificacionRepo = calificacionRepo;
        this.docenteConsulta = docenteConsulta;
        this.configRepo = configRepo;
        this.calculator = calculator;
    }

    public Vista ejecutar(UUID usuarioId, UUID cargaId, UUID periodoId) {

        var ctx = contexto.resolver(usuarioId);

        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));

        if (!ctx.profesorId().equals(carga.getProfesorId())) {
            throw new SecurityException("Esa clase no te pertenece.");
        }

        ConfiguracionAcademica cfg = configRepo.findByInstitucionId(ctx.institucionId())
                .orElseThrow(() -> new RuntimeException("Configura primero la escala de notas."));

        BigDecimal notaAprobacion = cfg.getNotaMinimaAprobacion();

        List<ActividadAcademica> actividades = actividadRepo
                .findByCargaYPeriodo(cargaId, periodoId)
                .stream()
                .filter(a -> a.getEstado() != EstadoActividad.ANULADA)
                .toList();

        Map<UUID, Map<UUID, BigDecimal>> notas = actividades.stream()
                .collect(Collectors.toMap(
                        ActividadAcademica::getId,
                        actividad -> calificacionRepo.findByActividadId(actividad.getId())
                                .stream()
                                .filter(c -> c.getNotaObtenida() != null)
                                .collect(Collectors.toMap(
                                        CalificacionActividad::getEstudianteId,
                                        CalificacionActividad::getNotaObtenida,
                                        (a, b) -> a
                                ))
                ));

        BigDecimal sumaPorcentajes = actividades.stream()
                .map(a -> a.getPorcentaje() != null ? a.getPorcentaje() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Fila> filas = docenteConsulta.estudiantesDeGrupo(carga.getGrupoId())
                .stream()
                .map(estudiante -> {

                    NotaFinalCalculator.Resultado resultado = calculator.calcular(
                            actividades,
                            notas,
                            estudiante.getEstudianteId(),
                            cfg
                    );

                    boolean aprueba = resultado.notaFinal() != null
                            && resultado.notaFinal().compareTo(notaAprobacion) >= 0;

                    return new Fila(
                            estudiante.getEstudianteId(),
                            estudiante.getCodigoEstudiante(),
                            estudiante.getNombre(),
                            resultado.notaFinal(),
                            aprueba,
                            resultado.actividadesCalificadas(),
                            actividades.size()
                    );
                })
                .toList();

        return new Vista(
                sumaPorcentajes,
                notaAprobacion,
                filas
        );
    }
}