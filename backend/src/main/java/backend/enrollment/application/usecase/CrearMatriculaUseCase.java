package backend.enrollment.application.usecase;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.domain.model.Grupo;
import backend.enrollment.application.port.MatriculaRepository;
import backend.enrollment.domain.model.EstadoMatricula;
import backend.enrollment.domain.model.Matricula;
import backend.enrollment.infrastructure.rest.dto.MatriculaRequestDto;
import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.EstadoEstudiante;
import backend.people.domain.model.Estudiante;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearMatriculaUseCase {
    static final String[] ROLES = { "COORDINADOR_ACADEMICO" };

    private final MatriculaRepository matriculaRepo;
    private final EstudianteRepository estudianteRepo;
    private final AnioLectivoRepository anioRepo;
    private final GrupoRepository grupoRepo;
    private final AutorizacionService auth;

    public CrearMatriculaUseCase(MatriculaRepository matriculaRepo, EstudianteRepository estudianteRepo,
                                 AnioLectivoRepository anioRepo, GrupoRepository grupoRepo,
                                 AutorizacionService auth) {
        this.matriculaRepo = matriculaRepo; this.estudianteRepo = estudianteRepo;
        this.anioRepo = anioRepo; this.grupoRepo = grupoRepo; this.auth = auth;
    }

    @Transactional
    public Matricula ejecutar(UUID usuarioId, MatriculaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ROLES);

        Estudiante est = estudianteRepo.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(est.getInstitucionId()))
            throw new SecurityException("El estudiante no pertenece a tu institución.");
        if (est.getEstado() != EstadoEstudiante.ACTIVO)
            throw new RuntimeException("El estudiante no está activo.");

        AnioLectivo anio = anioRepo.findById(dto.getAnioLectivoId())
                .orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(anio.getInstitucionId()))
            throw new SecurityException("El año lectivo no pertenece a tu institución.");

        if (matriculaRepo.findByInstitucionIdAndEstudianteIdAndAnioLectivoId(inst, est.getId(), anio.getId()).isPresent())
            throw new RuntimeException("El estudiante ya tiene matrícula en ese año lectivo.");

        // Grupo opcional: con grupo → MATRICULADO, sin grupo → PREMATRICULA
        EstadoMatricula estado = EstadoMatricula.PREMATRICULA;
        if (dto.getGrupoId() != null) {
            validarGrupo(inst, dto.getAnioLectivoId(), dto.getGrupoId());
            estado = EstadoMatricula.MATRICULADO;
        }

        LocalDateTime ahora = LocalDateTime.now();
        Matricula m = new Matricula();
        m.setId(UUID.randomUUID());
        m.setInstitucionId(inst);
        m.setEstudianteId(est.getId());
        m.setAnioLectivoId(anio.getId());
        m.setGrupoId(dto.getGrupoId());
        m.setCodigoMatricula("MAT-" + anio.getAnio() + "-" + est.getCodigoEstudiante());
        m.setTipo(dto.getTipo());
        m.setEstado(estado);
        m.setFechaMatricula(dto.getFechaMatricula() != null ? dto.getFechaMatricula() : LocalDate.now());
        m.setFechaEstado(LocalDate.now());
        m.setObservaciones(dto.getObservaciones());
        m.setCreatedBy(usuarioId);
        m.setUpdatedBy(usuarioId);
        m.setCreatedAt(ahora);
        m.setUpdatedAt(ahora);
        return matriculaRepo.save(m);
    }

    /** Valida institución, año y CUPO del grupo. Reutilizado por AsignarGrupoUseCase. */
    void validarGrupo(UUID inst, UUID anioId, UUID grupoId) {
        Grupo grupo = grupoRepo.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("El grupo no existe."));
        if (!inst.equals(grupo.getInstitucionId()))
            throw new SecurityException("El grupo no pertenece a tu institución.");
        if (!anioId.equals(grupo.getAnioLectivoId()))
            throw new RuntimeException("El grupo no pertenece a ese año lectivo.");
        if (grupo.getCupoMaximo() != null
                && matriculaRepo.contarMatriculadosEnGrupo(grupoId) >= grupo.getCupoMaximo())
            throw new RuntimeException("El grupo " + grupo.getNombre() + " ya alcanzó su cupo máximo ("
                    + grupo.getCupoMaximo() + ").");
    }
}