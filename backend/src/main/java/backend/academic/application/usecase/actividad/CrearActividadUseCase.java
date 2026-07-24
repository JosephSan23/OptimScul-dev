package backend.academic.application.usecase.actividad;

import backend.academic.application.port.*;
import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.*;
import backend.academic.infrastructure.rest.dto.ActividadAcademicaRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearActividadUseCase {
    private final ActividadAcademicaRepository actividadRepo;
    private final CargaAcademicaRepository cargaRepo;
    private final PeriodoAcademicoRepository periodoRepo;
    private final ConfiguracionAcademicaRepository configRepo;
    private final ContextoDocenteService contexto;

    public CrearActividadUseCase(ActividadAcademicaRepository actividadRepo, CargaAcademicaRepository cargaRepo,
                                 PeriodoAcademicoRepository periodoRepo, ConfiguracionAcademicaRepository configRepo,
                                 ContextoDocenteService contexto) {
        this.actividadRepo = actividadRepo; this.cargaRepo = cargaRepo; this.periodoRepo = periodoRepo;
        this.configRepo = configRepo; this.contexto = contexto;
    }

    @Transactional
    public ActividadAcademica ejecutar(UUID usuarioId, UUID cargaId, ActividadAcademicaRequestDto dto) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");

        PeriodoAcademico periodo = periodoRepo.findById(dto.getPeriodoAcademicoId())
                .orElseThrow(() -> new RuntimeException("El periodo no existe."));
        if (!carga.getAnioLectivoId().equals(periodo.getAnioLectivoId()))
            throw new RuntimeException("El periodo no pertenece al año lectivo de la clase.");

        BigDecimal notaMaxima = dto.getNotaMaxima();
        if (notaMaxima == null)
            notaMaxima = configRepo.findByInstitucionId(ctx.institucionId())
                    .map(ConfiguracionAcademica::getNotaMaxima).orElse(new BigDecimal("5.00"));
        if (notaMaxima.signum() <= 0)
            throw new RuntimeException("La nota máxima debe ser mayor que cero.");
        if (dto.getPorcentaje() != null &&
                (dto.getPorcentaje().signum() < 0 || dto.getPorcentaje().compareTo(new BigDecimal("100")) > 0))
            throw new RuntimeException("El porcentaje debe estar entre 0 y 100.");

        LocalDateTime ahora = LocalDateTime.now();
        ActividadAcademica a = new ActividadAcademica();
        a.setId(UUID.randomUUID());
        a.setInstitucionId(ctx.institucionId());
        a.setCargaAcademicaId(cargaId);
        a.setPeriodoAcademicoId(periodo.getId());
        a.setTipo(dto.getTipo());
        a.setTitulo(dto.getTitulo());
        a.setDescripcion(dto.getDescripcion());
        a.setPorcentaje(dto.getPorcentaje());
        a.setNotaMaxima(notaMaxima);
        a.setPermiteEntregaTardia(dto.getPermiteEntregaTardia() != null ? dto.getPermiteEntregaTardia() : false);
        a.setFechaPublicacion(ahora);
        a.setFechaEntrega(dto.getFechaEntrega() != null ? dto.getFechaEntrega().atStartOfDay() : null);
        a.setFechaCierre(dto.getFechaCierre() != null ? dto.getFechaCierre().atStartOfDay() : null);
        a.setEstado(EstadoActividad.PUBLICADA);
        a.setCreadaPorUsuarioId(usuarioId);
        a.setUpdatedBy(usuarioId);
        a.setCreatedAt(ahora);
        a.setUpdatedAt(ahora);
        return actividadRepo.save(a);
    }
}