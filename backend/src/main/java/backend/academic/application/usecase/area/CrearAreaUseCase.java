package backend.academic.application.usecase.area;

import backend.academic.application.port.AreaAcademicaRepository;
import backend.academic.domain.model.AreaAcademica;
import backend.academic.infrastructure.rest.dto.AreaAcademica.AreaAcademicaRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearAreaUseCase {
    private final AreaAcademicaRepository repo;
    private final AutorizacionService auth;

    public CrearAreaUseCase(AreaAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional
    public AreaAcademica ejecutar(UUID usuarioId, AreaAcademicaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAreasUseCase.ROLES);
        if (repo.existsByInstitucionIdAndCodigo(inst, dto.getCodigo()))
            throw new RuntimeException("Ya existe un área con el código " + dto.getCodigo() + ".");
        LocalDateTime ahora = LocalDateTime.now();
        AreaAcademica a = new AreaAcademica();
        a.setId(UUID.randomUUID());
        a.setInstitucionId(inst);
        a.setCodigo(dto.getCodigo());
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setActiva(true);
        a.setCreatedAt(ahora);
        a.setUpdatedAt(ahora);
        return repo.save(a);
    }
}