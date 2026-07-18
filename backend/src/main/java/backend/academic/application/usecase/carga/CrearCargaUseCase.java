package backend.academic.application.usecase.carga;

import backend.academic.application.port.*;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.domain.model.*;
import backend.academic.infrastructure.rest.dto.CargaAcademica.CargaAcademicaRequestDto;
import backend.people.application.port.ProfesorRepository;
import backend.people.domain.model.EstadoProfesor;
import backend.people.domain.model.Profesor;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearCargaUseCase {
    private final CargaAcademicaRepository cargaRepo;
    private final AnioLectivoRepository anioRepo;
    private final GrupoRepository grupoRepo;
    private final AsignaturaRepository asignaturaRepo;
    private final ProfesorRepository profesorRepo;
    private final AutorizacionService auth;

    public CrearCargaUseCase(CargaAcademicaRepository cargaRepo, AnioLectivoRepository anioRepo,
                             GrupoRepository grupoRepo, AsignaturaRepository asignaturaRepo,
                             ProfesorRepository profesorRepo, AutorizacionService auth) {
        this.cargaRepo = cargaRepo; this.anioRepo = anioRepo; this.grupoRepo = grupoRepo;
        this.asignaturaRepo = asignaturaRepo; this.profesorRepo = profesorRepo; this.auth = auth;
    }

    @Transactional
    public CargaAcademica ejecutar(UUID usuarioId, CargaAcademicaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarProfesoresUseCase.ROLES);

        AnioLectivo anio = anioRepo.findById(dto.getAnioLectivoId())
                .orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(anio.getInstitucionId()))
            throw new SecurityException("El año lectivo no pertenece a tu institución.");

        Grupo grupo = grupoRepo.findById(dto.getGrupoId())
                .orElseThrow(() -> new RuntimeException("El grupo no existe."));
        if (!inst.equals(grupo.getInstitucionId()))
            throw new SecurityException("El grupo no pertenece a tu institución.");
        if (!dto.getAnioLectivoId().equals(grupo.getAnioLectivoId()))
            throw new RuntimeException("El grupo no pertenece a ese año lectivo.");

        Asignatura asignatura = asignaturaRepo.findById(dto.getAsignaturaId())
                .orElseThrow(() -> new RuntimeException("La asignatura no existe."));
        if (!inst.equals(asignatura.getInstitucionId()))
            throw new SecurityException("La asignatura no pertenece a tu institución.");
        if (!Boolean.TRUE.equals(asignatura.getActiva()))
            throw new RuntimeException("La asignatura está inactiva.");

        Profesor profesor = profesorRepo.findById(dto.getProfesorId())
                .orElseThrow(() -> new RuntimeException("El profesor no existe."));
        if (!inst.equals(profesor.getInstitucionId()))
            throw new SecurityException("El profesor no pertenece a tu institución.");
        if (profesor.getEstado() != EstadoProfesor.ACTIVO)
            throw new RuntimeException("El profesor no está activo.");

        if (cargaRepo.existsActiva(inst, dto.getAnioLectivoId(), dto.getGrupoId(), dto.getAsignaturaId()))
            throw new RuntimeException("Ese grupo ya tiene un profesor activo para esa asignatura en ese año.");

        LocalDateTime ahora = LocalDateTime.now();
        CargaAcademica c = new CargaAcademica();
        c.setId(UUID.randomUUID());
        c.setInstitucionId(inst);
        c.setAnioLectivoId(dto.getAnioLectivoId());
        c.setGrupoId(dto.getGrupoId());
        c.setAsignaturaId(dto.getAsignaturaId());
        c.setProfesorId(dto.getProfesorId());
        c.setIntensidadHorariaSemanal(dto.getIntensidadHorariaSemanal() != null
                ? dto.getIntensidadHorariaSemanal()
                : asignatura.getIntensidadHorariaSemanal());   // hereda de la asignatura
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        c.setEstado(EstadoCargaAcademica.ACTIVA);
        c.setObservaciones(dto.getObservaciones());
        c.setCreatedAt(ahora);
        c.setUpdatedAt(ahora);
        return cargaRepo.save(c);
    }
}