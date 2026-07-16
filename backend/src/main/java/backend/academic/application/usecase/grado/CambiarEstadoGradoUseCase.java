package backend.academic.application.usecase.grado;

import backend.academic.application.port.GradoRepository;
import backend.academic.domain.model.Grado;
import backend.people.domain.model.EstadoRegistro;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoGradoUseCase {
    private final GradoRepository repo;
    private final AutorizacionService auth;

    public CambiarEstadoGradoUseCase(GradoRepository repo, AutorizacionService auth) {
        this.repo = repo;
        this.auth = auth;
    }

    @Transactional
    public void activar(UUID u, UUID id) {
        cambiar(u, id, EstadoRegistro.ACTIVO);
    }

    @Transactional
    public void inactivar(UUID u, UUID id) {
        cambiar(u, id, EstadoRegistro.INACTIVO);
    }

    private void cambiar(UUID usuarioId, UUID gradoId, EstadoRegistro estado) {
        UUID inst = auth.institucionConRol(usuarioId, ListarGradosUseCase.ROLES);
        Grado g = repo.findById(gradoId)
                .orElseThrow(() -> new RuntimeException("El grado no existe."));
        if (!inst.equals(g.getInstitucionId()))
            throw new SecurityException("No puedes cambiar grados de otra institución.");
        g.setEstado(estado);
        g.setUpdatedAt(LocalDateTime.now());
        repo.save(g);
    }
}