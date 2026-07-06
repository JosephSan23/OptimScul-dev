package backend.onboarding.application.usecase;

import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoAdministradorUseCase {

    private final UsuarioRepository usuarioRepository;
    private final AutorizacionService autorizacionService;

    public CambiarEstadoAdministradorUseCase(UsuarioRepository usuarioRepository,
                                             AutorizacionService autorizacionService) {
        this.usuarioRepository = usuarioRepository;
        this.autorizacionService = autorizacionService;
    }

    @Transactional
    public void activar(UUID superAdminId, UUID usuarioId) { cambiar(superAdminId, usuarioId, EstadoUsuario.ACTIVO); }

    @Transactional
    public void inactivar(UUID superAdminId, UUID usuarioId) { cambiar(superAdminId, usuarioId, EstadoUsuario.INACTIVO); }

    private void cambiar(UUID superAdminId, UUID usuarioId, EstadoUsuario nuevoEstado) {
        autorizacionService.exigirSuperAdmin(superAdminId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El administrador no existe."));
        usuario.setEstado(nuevoEstado);
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}