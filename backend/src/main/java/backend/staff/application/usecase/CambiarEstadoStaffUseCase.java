package backend.staff.application.usecase;

import backend.security.application.AutorizacionService;
import backend.security.application.EstadoUsuarioService;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.domain.model.UsuarioInstitucion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CambiarEstadoStaffUseCase {

    private final EstadoUsuarioService estadoUsuarioService;
    private final AutorizacionService autorizacionService;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;

    public CambiarEstadoStaffUseCase(EstadoUsuarioService estadoUsuarioService,
                                     AutorizacionService autorizacionService,
                                     UsuarioInstitucionRepository usuarioInstitucionRepository) {
        this.estadoUsuarioService = estadoUsuarioService;
        this.autorizacionService = autorizacionService;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
    }

    @Transactional
    public void activar(UUID adminId, UUID usuarioId)   { validar(adminId, usuarioId); estadoUsuarioService.activar(usuarioId); }

    @Transactional
    public void inactivar(UUID adminId, UUID usuarioId) { validar(adminId, usuarioId); estadoUsuarioService.inactivar(usuarioId); }

    private void validar(UUID adminId, UUID usuarioId) {
        UUID institucionAdmin = institucionDe(adminId);
        autorizacionService.exigirRolEnInstitucion(adminId, institucionAdmin, "ADMIN_INSTITUCION");
        if (!institucionAdmin.equals(institucionDe(usuarioId))) {
            throw new SecurityException("No puedes cambiar el estado de usuarios de otra institución.");
        }
    }

    private UUID institucionDe(UUID usuarioId) {
        return usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("El usuario no pertenece a ninguna institución."));
    }
}