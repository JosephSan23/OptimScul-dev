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
public class EditarAreaUseCase {
    private final AreaAcademicaRepository repo;
    private final AutorizacionService auth;

    public EditarAreaUseCase(AreaAcademicaRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional
    public AreaAcademica ejecutar(UUID usuarioId, UUID areaId, AreaAcademicaRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarAreasUseCase.ROLES);
        AreaAcademica a = repo.findById(areaId)
                .orElseThrow(() -> new RuntimeException("El área no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("No puedes editar áreas de otra institución.");
        if (!a.getCodigo().equalsIgnoreCase(dto.getCodigo())
                && repo.existsByInstitucionIdAndCodigo(inst, dto.getCodigo()))
            throw new RuntimeException("Ya existe un área con el código " + dto.getCodigo() + ".");
        a.setCodigo(dto.getCodigo());
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setUpdatedAt(LocalDateTime.now());
        return repo.save(a);
    }
}