package backend.academic.application.usecase.docente;

import backend.academic.application.port.*;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.*;
import backend.academic.infrastructure.rest.dto.Asistencia.GuardarAsistenciaRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GuardarAsistenciaUseCase {

    private final CargaAcademicaRepository cargaRepo;
    private final DocenteConsultaRepository consulta;
    private final SesionClaseRepository sesionRepo;
    private final AsistenciaClaseRepository asistenciaRepo;
    private final ContextoDocenteService contexto;

    public GuardarAsistenciaUseCase(CargaAcademicaRepository cargaRepo, DocenteConsultaRepository consulta,
                                    SesionClaseRepository sesionRepo, AsistenciaClaseRepository asistenciaRepo,
                                    ContextoDocenteService contexto) {
        this.cargaRepo = cargaRepo; this.consulta = consulta; this.sesionRepo = sesionRepo;
        this.asistenciaRepo = asistenciaRepo; this.contexto = contexto;
    }

    @Transactional
    public void ejecutar(UUID usuarioId, UUID cargaId, GuardarAsistenciaRequestDto dto) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");
        if (dto.getFecha().isAfter(LocalDate.now()))
            throw new RuntimeException("No puedes registrar asistencia de una fecha futura.");

        // Solo se aceptan estudiantes que estén matriculados en el grupo
        Set<UUID> validos = consulta.estudiantesDeGrupo(carga.getGrupoId()).stream()
                .map(EstudianteDeClase::getEstudianteId).collect(Collectors.toSet());

        LocalDateTime ahora = LocalDateTime.now();

        // Sesión: recuperar o crear (una por clase y fecha) — queda DICTADA
        SesionClase sesion = sesionRepo.findByCargaAcademicaIdAndFecha(cargaId, dto.getFecha())
                .orElseGet(() -> {
                    SesionClase s = new SesionClase();
                    s.setId(UUID.randomUUID());
                    s.setInstitucionId(ctx.institucionId());
                    s.setCargaAcademicaId(cargaId);
                    s.setFecha(dto.getFecha());
                    s.setEstado(EstadoSesionClase.DICTADA);
                    s.setFueReprogramada(false);
                    s.setCreatedBy(usuarioId);
                    s.setCreatedAt(ahora);
                    s.setUpdatedAt(ahora);
                    return s;
                });
        sesion.setTema(dto.getTema());
        sesion.setEstado(EstadoSesionClase.DICTADA);
        sesion.setUpdatedBy(usuarioId);
        sesion.setUpdatedAt(ahora);
        SesionClase sesionGuardada = sesionRepo.save(sesion);

        // Upsert de cada marca
        for (GuardarAsistenciaRequestDto.Marca m : dto.getMarcas()) {
            if (!validos.contains(m.getEstudianteId())) continue;   // ignora ajenos al grupo
            TipoAsistencia tipo = TipoAsistencia.valueOf(m.getTipo());

            AsistenciaClase a = asistenciaRepo
                    .findBySesionClaseIdAndEstudianteId(sesionGuardada.getId(), m.getEstudianteId())
                    .orElseGet(() -> {
                        AsistenciaClase nueva = new AsistenciaClase();
                        nueva.setId(UUID.randomUUID());
                        nueva.setSesionClaseId(sesionGuardada.getId());
                        nueva.setEstudianteId(m.getEstudianteId());
                        nueva.setCreatedAt(ahora);
                        return nueva;
                    });
            a.setTipoAsistencia(tipo);
            a.setMinutosTarde(tipo == TipoAsistencia.TARDE ? m.getMinutosTarde() : null);
            a.setObservacion(m.getObservacion());
            a.setRegistradaPorUsuarioId(usuarioId);
            a.setFechaRegistro(ahora);
            a.setUpdatedAt(ahora);
            asistenciaRepo.save(a);
        }
    }
}