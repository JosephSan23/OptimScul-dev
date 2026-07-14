package backend.config.application.usecase.periodoAcademico;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.application.port.PeriodoAcademicoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.domain.model.EstadoPeriodo;
import backend.academic.domain.model.PeriodoAcademico;
import backend.config.infrastructure.rest.dto.PeriodoConfigRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearPeriodoUseCase {
    private final PeriodoAcademicoRepository periodoRepo;
    private final AnioLectivoRepository anioRepo;
    private final AutorizacionService auth;
    public CrearPeriodoUseCase(PeriodoAcademicoRepository periodoRepo, AnioLectivoRepository anioRepo, AutorizacionService auth) {
        this.periodoRepo = periodoRepo; this.anioRepo = anioRepo; this.auth = auth;
    }
    @Transactional
    public PeriodoAcademico ejecutar(UUID adminId, UUID anioId, PeriodoConfigRequestDto dto) {
        UUID inst = auth.institucionDelAdmin(adminId);
        AnioLectivo anio = anioRepo.findById(anioId).orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(anio.getInstitucionId())) throw new SecurityException("No puedes agregar periodos a años de otra institución.");
        LocalDateTime ahora = LocalDateTime.now();
        PeriodoAcademico p = new PeriodoAcademico();
        p.setId(UUID.randomUUID());
        p.setInstitucionId(inst);
        p.setAnioLectivoId(anioId);
        p.setNumero(dto.getNumero() != null ? dto.getNumero().shortValue() : null);
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFin(dto.getFechaFin());
        p.setPeso(dto.getPeso());
        p.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoPeriodo.PLANEADO);
        p.setCreatedAt(ahora);
        p.setUpdatedAt(ahora);
        return periodoRepo.save(p);
    }
}