package backend.academic.application.usecase.docente;

import backend.academic.application.port.AsistenciaClaseRepository;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.EstudianteDeClase;
import backend.academic.application.port.SesionClaseRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.AsistenciaClase;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.SesionClase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ObtenerAsistenciaUseCase {

    public record Marca(UUID estudianteId, String codigoEstudiante, String nombre, String numeroDocumento,
                        String tipo, Short minutosTarde, String observacion) {}
    public record Vista(UUID sesionId, String fecha, String tema, List<Marca> estudiantes) {}

    private final DocenteConsultaRepository consulta;
    private final CargaAcademicaRepository cargaRepo;
    private final SesionClaseRepository sesionRepo;
    private final AsistenciaClaseRepository asistenciaRepo;
    private final ContextoDocenteService contexto;

    public ObtenerAsistenciaUseCase(DocenteConsultaRepository consulta, CargaAcademicaRepository cargaRepo,
                                    SesionClaseRepository sesionRepo, AsistenciaClaseRepository asistenciaRepo,
                                    ContextoDocenteService contexto) {
        this.consulta = consulta; this.cargaRepo = cargaRepo; this.sesionRepo = sesionRepo;
        this.asistenciaRepo = asistenciaRepo; this.contexto = contexto;
    }

    public Vista ejecutar(UUID usuarioId, UUID cargaId, LocalDate fecha) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");

        List<EstudianteDeClase> roster = consulta.estudiantesDeGrupo(carga.getGrupoId());

        SesionClase sesion = sesionRepo.findByCargaAcademicaIdAndFecha(cargaId, fecha).orElse(null);
        Map<UUID, AsistenciaClase> porEstudiante = sesion == null ? Map.of()
                : asistenciaRepo.findBySesionClaseId(sesion.getId()).stream()
                    .collect(Collectors.toMap(AsistenciaClase::getEstudianteId, a -> a, (x, y) -> x));

        List<Marca> marcas = roster.stream().map(e -> {
            AsistenciaClase a = porEstudiante.get(e.getEstudianteId());
            return new Marca(
                    e.getEstudianteId(), e.getCodigoEstudiante(), e.getNombre(), e.getNumeroDocumento(),
                    a != null ? a.getTipoAsistencia().name() : "PRESENTE",
                    a != null ? a.getMinutosTarde() : null,
                    a != null ? a.getObservacion() : null);
        }).toList();

        return new Vista(sesion != null ? sesion.getId() : null, fecha.toString(),
                sesion != null ? sesion.getTema() : null, marcas);
    }
}