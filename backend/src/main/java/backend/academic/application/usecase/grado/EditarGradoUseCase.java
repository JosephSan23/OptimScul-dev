package backend.academic.application.usecase.grado;

import backend.academic.application.port.GradoRepository;
import backend.academic.domain.model.Grado;
import backend.academic.infrastructure.rest.dto.Grado.GradoRequestDto;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarGradoUseCase {
    private final GradoRepository repo;
    private final AutorizacionService auth;

    public EditarGradoUseCase(GradoRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional
    public Grado ejecutar(UUID usuarioId, UUID gradoId, GradoRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGradosUseCase.ROLES);
        Grado g = repo.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("El grado no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("No puedes editar grados de otra institución.");
        if (!g.getCodigo().equalsIgnoreCase(dto.getCodigo())
                && repo.existsByInstitucionIdAndCodigo(inst, dto.getCodigo()))
            throw new RuntimeException("Ya existe un grado con el código " + dto.getCodigo() + ".");
        g.setCodigo(dto.getCodigo());
        g.setNombre(dto.getNombre());
        g.setNivel(dto.getNivel());
        g.setOrden(dto.getOrden());
        g.setUpdatedAt(LocalDateTime.now());
        return repo.save(g);
    }
}