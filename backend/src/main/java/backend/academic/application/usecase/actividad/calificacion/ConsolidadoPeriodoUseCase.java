package backend.academic.application.usecase.actividad.calificacion;

import backend.academic.application.port.*;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsolidadoPeriodoUseCase {

    public record Fila(UUID estudianteId, String codigoEstudiante, String nombre,
            BigDecimal notaFinal, boolean aprueba, int calificadas, int totalActividades) {
    }

    public record Vista(BigDecimal sumaPorcentajes, BigDecimal notaAprobacion, List<Fila> estudiantes) {
    }

    private final ContextoDocenteService contexto;
    private final CargaAcademicaRepository cargaRepo;
    private final ActividadAcademicaRepository actividadRepo;
    private final CalificacionActividadRepository calificacionRepo;
    private final DocenteConsultaRepository docenteConsulta;
    private final ConfiguracionAcademicaRepository configRepo;

    public ConsolidadoPeriodoUseCase(ContextoDocenteService contexto, CargaAcademicaRepository cargaRepo,
            ActividadAcademicaRepository actividadRepo,
            CalificacionActividadRepository calificacionRepo,
            DocenteConsultaRepository docenteConsulta,
            ConfiguracionAcademicaRepository configRepo) {
        this.contexto = contexto;
        this.cargaRepo = cargaRepo;
        this.actividadRepo = actividadRepo;
        this.calificacionRepo = calificacionRepo;
        this.docenteConsulta = docenteConsulta;
        this.configRepo = configRepo;
    }

    public Vista ejecutar(UUID usuarioId, UUID cargaId, UUID periodoId) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");

        ConfiguracionAcademica cfg = configRepo.findByInstitucionId(ctx.institucionId())
                .orElseThrow(() -> new RuntimeException("Configura primero la escala de notas."));
        int decimales = cfg.getDecimalesNota() != null ? cfg.getDecimalesNota() : 2;
        BigDecimal escalaMax = cfg.getNotaMaxima();
        BigDecimal aprob = cfg.getNotaMinimaAprobacion();

        // Actividades vigentes del periodo (excluye anuladas)
        List<ActividadAcademica> actividades = actividadRepo.findByCargaYPeriodo(cargaId, periodoId).stream()
                .filter(a -> a.getEstado() != EstadoActividad.ANULADA)
                .toList();

        // notas: actividadId -> (estudianteId -> nota)
        Map<UUID, Map<UUID, BigDecimal>> notas = actividades.stream().collect(Collectors.toMap(
                ActividadAcademica::getId,
                a -> calificacionRepo.findByActividadId(a.getId()).stream()
                        .filter(c -> c.getNotaObtenida() != null)
                        .collect(Collectors.toMap(CalificacionActividad::getEstudianteId,
                                CalificacionActividad::getNotaObtenida, (x, y) -> x))));

        BigDecimal sumaPorc = actividades.stream()
                .map(a -> a.getPorcentaje() != null ? a.getPorcentaje() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Fila> filas = docenteConsulta.estudiantesDeGrupo(carga.getGrupoId()).stream().map(e -> {
            BigDecimal sumW = BigDecimal.ZERO, sumWF = BigDecimal.ZERO;   // ponderado (peso > 0)
            BigDecimal sumFrac = BigDecimal.ZERO;                          // promedio simple
            int nFrac = 0, calif = 0;

            for (ActividadAcademica a : actividades) {
                BigDecimal nota = notas.getOrDefault(a.getId(), Map.of()).get(e.getEstudianteId());
                if (nota == null) continue;                    // sin calificar → no cuenta
                calif++;
                BigDecimal frac = nota.divide(a.getNotaMaxima(), 6, RoundingMode.HALF_UP);   // 0..1

                sumFrac = sumFrac.add(frac);                   // acumula para el promedio simple
                nFrac++;

                BigDecimal peso = a.getPorcentaje();
                if (peso != null && peso.signum() > 0) {       // solo las que tienen peso van al ponderado
                    sumWF = sumWF.add(frac.multiply(peso));
                    sumW = sumW.add(peso);
                }
            }

            BigDecimal notaFinal = null;
            if (sumW.signum() > 0) {
                // hay pesos → promedio ponderado
                notaFinal = sumWF.divide(sumW, 6, RoundingMode.HALF_UP)
                        .multiply(escalaMax).setScale(decimales, RoundingMode.HALF_UP);
            } else if (nFrac > 0) {
                // nadie tiene peso → promedio simple (suma / cantidad)
                notaFinal = sumFrac.divide(BigDecimal.valueOf(nFrac), 6, RoundingMode.HALF_UP)
                        .multiply(escalaMax).setScale(decimales, RoundingMode.HALF_UP);
            }

            boolean aprueba = notaFinal != null && notaFinal.compareTo(aprob) >= 0;
            return new Fila(e.getEstudianteId(), e.getCodigoEstudiante(), e.getNombre(),
                    notaFinal, aprueba, calif, actividades.size());
        }).toList();

        return new Vista(sumaPorc, aprob, filas);
    }
}
