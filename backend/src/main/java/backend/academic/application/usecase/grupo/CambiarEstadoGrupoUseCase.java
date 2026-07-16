package backend.academic.application.usecase.grupo;

import backend.academic.application.port.GrupoRepository;
import backend.academic.domain.model.EstadoGrupo;
import backend.academic.domain.model.Grupo;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoGrupoUseCase {
    private final GrupoRepository repo;
    private final AutorizacionService auth;

    public CambiarEstadoGrupoUseCase(GrupoRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    @Transactional
    public void activar(UUID u, UUID id) {
        cambiar(u, id, EstadoGrupo.ACTIVO);
    }

    @Transactional
    public void inactivar(UUID u, UUID id) {
        cambiar(u, id, EstadoGrupo.INACTIVO);
    }

    @Transactional
    public void cerrar(UUID u, UUID id) {
        cambiar(u, id, EstadoGrupo.CERRADO);
    }

    private void cambiar(UUID usuarioId, UUID grupoId, EstadoGrupo estado) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGruposPorGradoUseCase.ROLES);
        Grupo g = repo.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("El grupo no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("No puedes cambiar grupos de otra institución.");
        g.setEstado(estado);
        g.setUpdatedAt(LocalDateTime.now());
        repo.save(g);
    }
}