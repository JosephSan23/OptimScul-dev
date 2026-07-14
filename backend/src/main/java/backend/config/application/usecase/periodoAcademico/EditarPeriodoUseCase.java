package backend.config.application.usecase.periodoAcademico;

import backend.academic.application.port.PeriodoAcademicoRepository;
import backend.academic.domain.model.PeriodoAcademico;
import backend.config.infrastructure.rest.dto.PeriodoConfigRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarPeriodoUseCase {
    private final PeriodoAcademicoRepository periodoRepo;
    private final AutorizacionService auth;
    public EditarPeriodoUseCase(PeriodoAcademicoRepository periodoRepo, AutorizacionService auth) {
        this.periodoRepo = periodoRepo; this.auth = auth;
    }
    @Transactional
    public PeriodoAcademico ejecutar(UUID adminId, UUID periodoId, PeriodoConfigRequestDto dto) {
        UUID inst = auth.institucionDelAdmin(adminId);
        PeriodoAcademico p = periodoRepo.findById(periodoId).orElseThrow(() -> new RuntimeException("El periodo no existe."));
        if (!inst.equals(p.getInstitucionId())) throw new SecurityException("No puedes editar periodos de otra institución.");
        p.setNumero(dto.getNumero() != null ? dto.getNumero().shortValue() : null);
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFin(dto.getFechaFin());
        p.setPeso(dto.getPeso());
        if (dto.getEstado() != null) p.setEstado(dto.getEstado());
        p.setUpdatedAt(LocalDateTime.now());
        return periodoRepo.save(p);
    }
}