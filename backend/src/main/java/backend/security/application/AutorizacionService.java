package backend.security.application;

import backend.security.application.port.UsuarioRepository;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AutorizacionService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;

    public AutorizacionService(UsuarioRepository usuarioRepository, UsuarioInstitucionRepository usuarioInstitucionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
    }

    // ---- Super admin: PLATAFORMA y sin rol VISITANTE ----
    public boolean esSuperAdmin(UUID usuarioId) {
        Usuario u = usuarioRepository.findById(usuarioId).orElse(null);
        if (u == null) return false;
        List<String> roles = usuarioRepository.findRolesByUsuarioId(usuarioId);
        return u.getTipoContexto() == TipoContextoUsuario.PLATAFORMA && !roles.contains("VISITANTE");
    }

    public void exigirSuperAdmin(UUID usuarioId) {
        if (!esSuperAdmin(usuarioId)) {
            throw new SecurityException("Solo el super administrador puede realizar esta acción.");
        }
    }

    // ---- Rol en cualquier institución ----
    public boolean tieneAlgunRol(UUID usuarioId, String... roles) {
        List<String> propios = usuarioRepository.findRolesByUsuarioId(usuarioId);
        return Arrays.stream(roles).anyMatch(propios::contains);
    }

    public void exigirRol(UUID usuarioId, String... roles) {
        if (!tieneAlgunRol(usuarioId, roles)) {
            throw new SecurityException("No tienes el rol necesario para esta acción.");
        }
    }

    // ---- Rol DENTRO de una institución específica (scope) ----
    public boolean tieneRolEnInstitucion(UUID usuarioId, UUID institucionId, String... roles) {
        List<String> propios = usuarioRepository.findRolesByUsuarioIdAndInstitucion(usuarioId, institucionId);
        return Arrays.stream(roles).anyMatch(propios::contains);
    }

    public void exigirRolEnInstitucion(UUID usuarioId, UUID institucionId, String... roles) {
        if (!tieneRolEnInstitucion(usuarioId, institucionId, roles)) {
            throw new SecurityException("No tienes permisos sobre esta institución.");
        }
    }

    public UUID institucionDelAdmin(UUID adminId) {
        return institucionConRol(adminId, "ADMIN_INSTITUCION");
    }

    public UUID institucionConRol(UUID usuarioId, String... rolesPermitidos) {
        UUID institucionId = usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new SecurityException("No perteneces a ninguna institución."));
        exigirRolEnInstitucion(usuarioId, institucionId, rolesPermitidos);
        return institucionId;
    }
}