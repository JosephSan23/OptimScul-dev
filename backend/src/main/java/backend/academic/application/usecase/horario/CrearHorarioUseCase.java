package backend.academic.application.usecase.horario;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.HorarioCargaRepository;
import backend.academic.application.port.Horario.HorarioConsultaRepository;
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
public class CrearHorarioUseCase {
    private final HorarioCargaRepository horarioRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final HorarioConsultaRepository consulta;
    private final AutorizacionService auth;

    public CrearHorarioUseCase(HorarioCargaRepository horarioRepo, CargaAcademicaRepository cargaRepo,
                               HorarioConsultaRepository consulta, AutorizacionService auth) {
        this.horarioRepo = horarioRepo; this.cargaRepo = cargaRepo; this.consulta = consulta; this.auth = auth;
    }

    @Transactional
    public HorarioCarga ejecutar(UUID usuarioId, HorarioCargaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarHorariosPorGrupoUseCase.ROLES);

        CargaAcademica carga = cargaRepo.findById(dto.getCargaAcademicaId())
                .orElseThrow(() -> new RuntimeException("La carga académica no existe."));
        if (!inst.equals(carga.getInstitucionId()))
            throw new SecurityException("La carga no pertenece a tu institución.");
        if (carga.getEstado() != EstadoCargaAcademica.ACTIVA)
            throw new RuntimeException("Solo se pueden programar horarios de cargas activas.");

        validarFranja(dto);
        validarChoques(carga, dto, new UUID(0, 0));   // UUID "nulo": no excluye nada al crear

        LocalDateTime ahora = LocalDateTime.now();
        HorarioCarga h = new HorarioCarga();
        h.setId(UUID.randomUUID());
        h.setInstitucionId(inst);
        h.setCargaAcademicaId(carga.getId());
        h.setSedeId(dto.getSedeId());
        h.setDiaSemana(dto.getDiaSemana());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        h.setAula(dto.getAula());
        h.setActivo(true);
        h.setCreatedAt(ahora);
        h.setUpdatedAt(ahora);
        return horarioRepo.save(h);
    }

    static void validarFranja(HorarioCargaRequestDto dto) {
        if (!dto.getHoraInicio().isBefore(dto.getHoraFin()))
            throw new RuntimeException("La hora de inicio debe ser anterior a la hora de fin.");
    }

    void validarChoques(CargaAcademica carga, HorarioCargaRequestDto dto, UUID excluirId) {
        String dia = dto.getDiaSemana().name();
        String ini = dto.getHoraInicio().toString();
        String fin = dto.getHoraFin().toString();
        if (consulta.existeChoqueGrupo(carga.getGrupoId(), carga.getAnioLectivoId(), dia, ini, fin, excluirId))
            throw new RuntimeException("El grupo ya tiene otra clase en esa franja del " + dia.toLowerCase() + ".");
        if (consulta.existeChoqueProfesor(carga.getProfesorId(), carga.getAnioLectivoId(), dia, ini, fin, excluirId))
            throw new RuntimeException("El profesor ya tiene otra clase en esa franja del " + dia.toLowerCase() + ".");
    }
}