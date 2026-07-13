package backend.config.application.usecase.AnioLectivo;

import backend.academic.application.port.AnioLectivoRepository;
import backend.academic.domain.model.AnioLectivo;
import backend.academic.domain.model.EstadoAnioLectivo;
import backend.config.infrastructure.rest.dto.AnioLectivoConfigRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearAnioLectivoUseCase {
    private final AnioLectivoRepository repo;
    private final AutorizacionService auth;
    public CrearAnioLectivoUseCase(AnioLectivoRepository repo, AutorizacionService auth) { this.repo = repo; this.auth = auth; }
    @Transactional
    public AnioLectivo ejecutar(UUID adminId, AnioLectivoConfigRequestDto dto) {
        UUID inst = auth.institucionDelAdmin(adminId);
        LocalDateTime ahora = LocalDateTime.now();
        AnioLectivo a = new AnioLectivo();
        a.setId(UUID.randomUUID());
        a.setInstitucionId(inst);
        a.setAnio(dto.getAnio() != null ? dto.getAnio().shortValue() : null);
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setFechaInicio(dto.getFechaInicio());
        a.setFechaFin(dto.getFechaFin());
        a.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoAnioLectivo.PLANEACION);
        a.setEsActual(false);
        a.setCreatedAt(ahora);
        a.setUpdatedAt(ahora);
        return repo.save(a);
    }
}