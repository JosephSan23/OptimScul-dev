package backend.academic.application.usecase.horario;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.HorarioCargaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.HorarioCarga;
import backend.academic.infrastructure.rest.dto.Horario.HorarioCargaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarHorarioUseCase {
    private final HorarioCargaRepository horarioRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final CrearHorarioUseCase crearUseCase;   // reutiliza sus validaciones
    private final AutorizacionService auth;

    public EditarHorarioUseCase(HorarioCargaRepository horarioRepo, CargaAcademicaRepository cargaRepo,
                                CrearHorarioUseCase crearUseCase, AutorizacionService auth) {
        this.horarioRepo = horarioRepo; this.cargaRepo = cargaRepo;
        this.crearUseCase = crearUseCase; this.auth = auth;
    }

    @Transactional
    public HorarioCarga ejecutar(UUID usuarioId, UUID horarioId, HorarioCargaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarHorariosPorGrupoUseCase.ROLES);
        HorarioCarga h = horarioRepo.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("El horario no existe."));
        if (!inst.equals(h.getInstitucionId()))
            throw new SecurityException("No puedes editar horarios de otra institución.");

        // La carga es fija: el contexto de choques se toma de la carga original
        CargaAcademica carga = cargaRepo.findById(h.getCargaAcademicaId())
                .orElseThrow(() -> new RuntimeException("La carga académica no existe."));

        CrearHorarioUseCase.validarFranja(dto);
        crearUseCase.validarChoques(carga, dto, horarioId);   // se excluye a sí mismo

        h.setSedeId(dto.getSedeId());
        h.setDiaSemana(dto.getDiaSemana());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        h.setAula(dto.getAula());
        h.setUpdatedAt(LocalDateTime.now());
        return horarioRepo.save(h);
    }
}