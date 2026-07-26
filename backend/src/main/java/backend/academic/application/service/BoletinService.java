package backend.academic.application.service;


import backend.academic.application.port.*;
import backend.academic.application.port.CargaAcademica.*;
import backend.academic.application.usecase.actividad.calificacion.NotaFinalCalculator;
import backend.academic.domain.model.ActividadAcademica;
import backend.academic.domain.model.ConfiguracionAcademica;
import backend.enrollment.application.port.MatriculaRepository;
import backend.enrollment.domain.model.Matricula;
import backend.people.application.port.EstudianteRepository;
import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Estudiante;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BoletinService {

    public record MateriaNota(String asignaturaNombre, String profesorNombre, BigDecimal notaFinal, boolean aprueba) {}
   public record Vista(boolean matriculado, String institucionNombre, String estudianteNombre,
                        String gradoNombre, String grupoNombre, boolean boletinHabilitado,
                        BigDecimal promedio, BigDecimal notaAprobacion, List<MateriaNota> materias) {}

    private final EstudianteRepository estudianteRepo;
    private final MatriculaRepository matriculaRepo;
    private final CargaConsultaRepository cargaConsulta;
    private final ActividadAcademicaRepository actividadRepo;
    private final CalificacionActividadRepository calificacionRepo;
    private final ConfiguracionAcademicaRepository configRepo;
    private final NotaFinalCalculator calculadora;
    private final PeriodoAcademicoRepository periodoRepo;
    private final InstitucionRepository institucionRepo;
    private final PersonaRepository personaRepo;

    public BoletinService(EstudianteRepository estudianteRepo, MatriculaRepository matriculaRepo,
                          CargaConsultaRepository cargaConsulta, ActividadAcademicaRepository actividadRepo,
                          CalificacionActividadRepository calificacionRepo, ConfiguracionAcademicaRepository configRepo,
                          NotaFinalCalculator calculadora, PeriodoAcademicoRepository periodoRepo, InstitucionRepository institucionRepo,
                          PersonaRepository personaRepo) {
        this.estudianteRepo = estudianteRepo; this.matriculaRepo = matriculaRepo; this.cargaConsulta = cargaConsulta;
        this.actividadRepo = actividadRepo; this.calificacionRepo = calificacionRepo;
        this.configRepo = configRepo; this.calculadora = calculadora;
        this.periodoRepo = periodoRepo; this.institucionRepo = institucionRepo; this.personaRepo = personaRepo;
    }

    /** Boletín de un estudiante ya autorizado por el caller. */
    public Vista calcular(UUID estudianteId, UUID anioId, UUID periodoId) {
        Estudiante est = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        UUID inst = est.getInstitucionId();

        ConfiguracionAcademica cfg = configRepo.findByInstitucionId(inst)
                .orElseThrow(() -> new RuntimeException("La institución no tiene configuración académica."));
        BigDecimal aprob = cfg.getNotaMinimaAprobacion();
        int decimales = cfg.getDecimalesNota() != null ? cfg.getDecimalesNota() : 2;

        String institucionNombre = institucionRepo.findById(inst).map(i -> i.getNombre()).orElse("");
        String estudianteNombre = personaRepo.findById(est.getPersonaId())
                .map(p -> (p.getPrimerNombre() + " " + p.getPrimerApellido()).trim()).orElse("");
        boolean boletinHab = periodoRepo.findById(periodoId)
                .map(pa -> Boolean.TRUE.equals(pa.getBoletinHabilitado())).orElse(false);

        Matricula mat = matriculaRepo
                .findByInstitucionIdAndEstudianteIdAndAnioLectivoId(inst, estudianteId, anioId)
                .orElse(null);
        if (mat == null || mat.getGrupoId() == null)
            return new Vista(false, institucionNombre, estudianteNombre, null, null, boletinHab, null, aprob, List.of());

        UUID grupoId = mat.getGrupoId();
        List<CargaResumen> cargas = cargaConsulta.listarPorAnio(inst, anioId).stream()
                .filter(c -> grupoId.equals(c.getGrupoId()) && "ACTIVA".equals(c.getEstado()))
                .toList();

        String gradoNombre = cargas.isEmpty() ? null : cargas.get(0).getGradoNombre();
        String grupoNombre = cargas.isEmpty() ? null : cargas.get(0).getGrupoNombre();

        List<MateriaNota> materias = new ArrayList<>();
        BigDecimal suma = BigDecimal.ZERO; int conNota = 0;

        for (CargaResumen carga : cargas) {
            List<ActividadAcademica> acts = actividadRepo.findByCargaYPeriodo(carga.getId(), periodoId);
            Map<UUID, Map<UUID, BigDecimal>> notas = new HashMap<>();
            for (ActividadAcademica a : acts) {
                calificacionRepo.findByActividadIdAndEstudianteId(a.getId(), estudianteId)
                        .filter(c -> c.getNotaObtenida() != null)
                        .ifPresent(c -> notas.put(a.getId(), Map.of(estudianteId, c.getNotaObtenida())));
            }
            NotaFinalCalculator.Resultado res = calculadora.calcular(acts, notas, estudianteId, cfg);
            BigDecimal notaFinal = res.notaFinal();
            boolean aprueba = notaFinal != null && notaFinal.compareTo(aprob) >= 0;
            if (notaFinal != null) { suma = suma.add(notaFinal); conNota++; }
            materias.add(new MateriaNota(carga.getAsignaturaNombre(), carga.getProfesorNombre(), notaFinal, aprueba));
        }

        

        BigDecimal promedio = conNota > 0
                ? suma.divide(BigDecimal.valueOf(conNota), decimales, RoundingMode.HALF_UP) : null;
        return new Vista(true, institucionNombre, estudianteNombre, gradoNombre, grupoNombre, boletinHab, promedio, aprob, materias);
    }
}