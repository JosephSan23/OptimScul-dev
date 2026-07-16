package backend.academic.application.usecase.grado;

import backend.academic.application.port.GradoRepository;
import backend.academic.domain.model.Grado;
import backend.academic.infrastructure.rest.dto.Grado.GradoRequestDto;
import backend.people.domain.model.EstadoRegistro;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearGradoUseCase {
    private final GradoRepository repo;
    private final AutorizacionService auth;

    public CrearGradoUseCase(GradoRepository repo, AutorizacionService auth) {
        this.repo = repo; this.auth = auth;
    }

    @Transactional
    public Grado ejecutar(UUID usuarioId, GradoRequestDto dto) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGradosUseCase.ROLES);
        if (repo.existsByInstitucionIdAndCodigo(inst, dto.getCodigo()))
            throw new RuntimeException("Ya existe un grado con el código " + dto.getCodigo() + ".");
        LocalDateTime ahora = LocalDateTime.now();
        Grado g = new Grado();
        g.setId(UUID.randomUUID());
        g.setInstitucionId(inst);
        g.setCodigo(dto.getCodigo());
        g.setNombre(dto.getNombre());
        g.setNivel(dto.getNivel());
        g.setOrden(dto.getOrden());
        g.setEstado(EstadoRegistro.ACTIVO);
        g.setCreatedAt(ahora);
        g.setUpdatedAt(ahora);
        return repo.save(g);
    }
}