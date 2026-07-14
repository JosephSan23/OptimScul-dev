package backend.config.application.usecase.anioLectivo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.config.infrastructure.rest.dto.AnioLectivoConfigRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarAnioLectivoUseCase {
    private final AnioLectivoRepository repo;
    private final AutorizacionService auth;
    public EditarAnioLectivoUseCase(AnioLectivoRepository repo, AutorizacionService auth) { this.repo = repo; this.auth = auth; }
    @Transactional
    public AnioLectivo ejecutar(UUID adminId, UUID id, AnioLectivoConfigRequestDto dto) {
        UUID inst = auth.institucionDelAdmin(adminId);
        AnioLectivo a = repo.findById(id).orElseThrow(() -> new RuntimeException("El año lectivo no existe."));
        if (!inst.equals(a.getInstitucionId())) throw new SecurityException("No puedes editar años de otra institución.");
        a.setAnio(dto.getAnio() != null ? dto.getAnio().shortValue() : null);
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setFechaInicio(dto.getFechaInicio());
        a.setFechaFin(dto.getFechaFin());
        if (dto.getEstado() != null) a.setEstado(dto.getEstado());
        a.setUpdatedAt(LocalDateTime.now());
        return repo.save(a);
    }
}