package backend.academic.application.usecase.carga;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.domain.model.CargaAcademica;
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
public class EditarCargaUseCase {
    private final CargaAcademicaRepository cargaRepo;
    private final ProfesorRepository profesorRepo;
    private final AutorizacionService auth;

    public EditarCargaUseCase(CargaAcademicaRepository cargaRepo, ProfesorRepository profesorRepo,
                              AutorizacionService auth) {
        this.cargaRepo = cargaRepo; this.profesorRepo = profesorRepo; this.auth = auth;
    }

    @Transactional
    public CargaAcademica ejecutar(UUID usuarioId, UUID cargaId, CargaAcademicaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarProfesoresUseCase.ROLES);
        CargaAcademica c = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La carga académica no existe."));
        if (!inst.equals(c.getInstitucionId()))
            throw new SecurityException("No puedes editar cargas de otra institución.");

        // Grupo, asignatura y año NO se cambian: para eso se cancela y se crea otra.
        Profesor profesor = profesorRepo.findById(dto.getProfesorId())
                .orElseThrow(() -> new RuntimeException("El profesor no existe."));
        if (!inst.equals(profesor.getInstitucionId()))
            throw new SecurityException("El profesor no pertenece a tu institución.");
        if (profesor.getEstado() != EstadoProfesor.ACTIVO)
            throw new RuntimeException("El profesor no está activo.");

        c.setProfesorId(dto.getProfesorId());
        c.setIntensidadHorariaSemanal(dto.getIntensidadHorariaSemanal());
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        c.setObservaciones(dto.getObservaciones());
        c.setUpdatedAt(LocalDateTime.now());
        return cargaRepo.save(c);
    }
}