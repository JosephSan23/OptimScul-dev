package backend.academic.application.usecase.horario;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.HorarioCargaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.EstadoCargaAcademica;
import backend.academic.domain.model.HorarioCarga;
import backend.academic.infrastructure.rest.dto.Horario.HorarioCargaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoHorarioUseCase {
    private final HorarioCargaRepository horarioRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final CrearHorarioUseCase crearUseCase; // reutiliza validarChoques
    private final AutorizacionService auth;

    public CambiarEstadoHorarioUseCase(HorarioCargaRepository horarioRepo, CargaAcademicaRepository cargaRepo,
            CrearHorarioUseCase crearUseCase, AutorizacionService auth) {
        this.horarioRepo = horarioRepo;
        this.cargaRepo = cargaRepo;
        this.crearUseCase = crearUseCase;
        this.auth = auth;
    }

    @Transactional
    public void activar(UUID usuarioId, UUID horarioId) {
        HorarioCarga h = validarYObtener(usuarioId, horarioId);

        CargaAcademica carga = cargaRepo.findById(h.getCargaAcademicaId())
                .orElseThrow(() -> new RuntimeException("La carga académica no existe."));
        if (carga.getEstado() != EstadoCargaAcademica.ACTIVA)
            throw new RuntimeException("No se puede reactivar el horario: la carga ya no está activa.");

        // Mientras estuvo apagada pudieron ocupar su franja — se revalida como si fuera
        // nueva
        HorarioCargaRequestDto dto = new HorarioCargaRequestDto();
        dto.setCargaAcademicaId(h.getCargaAcademicaId());
        dto.setDiaSemana(h.getDiaSemana());
        dto.setHoraInicio(h.getHoraInicio());
        dto.setHoraFin(h.getHoraFin());
        crearUseCase.validarChoques(carga, dto, horarioId); // se excluye a sí misma

        h.setActivo(true);
        h.setUpdatedAt(LocalDateTime.now());
        horarioRepo.save(h);
    }

    @Transactional
    public void inactivar(UUID usuarioId, UUID horarioId) {
        HorarioCarga h = validarYObtener(usuarioId, horarioId);
        h.setActivo(false);
        h.setUpdatedAt(LocalDateTime.now());
        horarioRepo.save(h);
    }

    private HorarioCarga validarYObtener(UUID usuarioId, UUID horarioId) {
        UUID inst = auth.institucionConRol(usuarioId, ListarHorariosPorGrupoUseCase.ROLES);
        HorarioCarga h = horarioRepo.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("El horario no existe."));
        if (!inst.equals(h.getInstitucionId()))
            throw new SecurityException("No puedes cambiar horarios de otra institución.");
        return h;
    }
}