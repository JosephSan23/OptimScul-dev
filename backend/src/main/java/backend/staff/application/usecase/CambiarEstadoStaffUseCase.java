package backend.staff.application.usecase;

import backend.people.application.PerfilProfesorService;
import backend.security.application.AutorizacionService;
import backend.security.application.EstadoUsuarioService;
import backend.security.application.port.RolRepository;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.application.port.UsuarioRepository;
import backend.security.application.port.UsuarioRolRepository;
import backend.security.domain.model.Rol;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.domain.model.UsuarioRol;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CambiarEstadoStaffUseCase {

    private static final Set<String> ROLES_STAFF =
            Set.of("RECTOR", "COORDINADOR_ACADEMICO", "SECRETARIA", "DOCENTE", "TESORERIA");

    private final EstadoUsuarioService estadoUsuarioService;
    private final AutorizacionService autorizacionService;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;
    private final PerfilProfesorService perfilProfesorService;

    public CambiarEstadoStaffUseCase(EstadoUsuarioService estadoUsuarioService,
                                     AutorizacionService autorizacionService,
                                     UsuarioInstitucionRepository usuarioInstitucionRepository,
                                     UsuarioRepository usuarioRepository,
                                     UsuarioRolRepository usuarioRolRepository,
                                     RolRepository rolRepository,
                                     PerfilProfesorService perfilProfesorService) {
        this.estadoUsuarioService = estadoUsuarioService;
        this.autorizacionService = autorizacionService;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.rolRepository = rolRepository;
        this.perfilProfesorService = perfilProfesorService;
    }

    /** Reintegrar al staff: revive el cargo, el perfil de profesor si era docente, y la cuenta. */
    @Transactional
    public void activar(UUID adminId, UUID usuarioId) {
        validar(adminId, usuarioId);
        UUID inst = institucionDe(usuarioId);
        Usuario u = usuario(usuarioId);
        Set<UUID> idsStaff = idsRolesStaff();
        UUID idDocente = idRol("DOCENTE");
        LocalDateTime ahora = LocalDateTime.now();

        boolean eraDocente = false;
        for (UsuarioRol ur : filasEnInstitucion(usuarioId, inst)) {
            if (idsStaff.contains(ur.getRolId()) && !Boolean.TRUE.equals(ur.getActivo())) {
                ur.setActivo(true);
                ur.setUpdatedAt(ahora);
                usuarioRolRepository.save(ur);
                if (ur.getRolId().equals(idDocente)) eraDocente = true;
            }
        }
        if (eraDocente) {
            perfilProfesorService.reactivarPerfilSiExiste(inst, u.getPersonaId());
        }
        estadoUsuarioService.activar(usuarioId);   // la cuenta debe poder entrar de nuevo
    }

    /** Retirar del staff: apaga el cargo y el perfil de profesor.
     *  La cuenta solo se bloquea si NO le quedan otros roles (acudiente, etc.). */
    @Transactional
    public void inactivar(UUID adminId, UUID usuarioId) {
        validar(adminId, usuarioId);
        UUID inst = institucionDe(usuarioId);
        Usuario u = usuario(usuarioId);
        Set<UUID> idsStaff = idsRolesStaff();
        LocalDateTime ahora = LocalDateTime.now();

        List<UsuarioRol> filas = filasEnInstitucion(usuarioId, inst);

        // ¿Tiene roles activos que NO son de staff? (calcularlo ANTES de apagar nada)
        boolean tieneOtrosRoles = filas.stream()
                .anyMatch(ur -> Boolean.TRUE.equals(ur.getActivo()) && !idsStaff.contains(ur.getRolId()));

        // Apagar solo las filas del cargo staff
        filas.stream()
                .filter(ur -> idsStaff.contains(ur.getRolId()))
                .filter(ur -> Boolean.TRUE.equals(ur.getActivo()))
                .forEach(ur -> {
                    ur.setActivo(false);
                    ur.setUpdatedAt(ahora);
                    usuarioRolRepository.save(ur);
                });

        // Su perfil de profesor (si existe) queda retirado
        perfilProfesorService.inactivarPerfil(inst, u.getPersonaId());

        // La cuenta entera solo se apaga si no le queda ningún otro sombrero
        if (!tieneOtrosRoles) {
            estadoUsuarioService.inactivar(usuarioId);
        }
    }

    // ───────────────────── helpers ─────────────────────

    private void validar(UUID adminId, UUID usuarioId) {
        UUID institucionAdmin = institucionDe(adminId);
        autorizacionService.exigirRolEnInstitucion(adminId, institucionAdmin, "ADMIN_INSTITUCION");
        if (!institucionAdmin.equals(institucionDe(usuarioId))) {
            throw new SecurityException("No puedes cambiar el estado de usuarios de otra institución.");
        }
    }

    private List<UsuarioRol> filasEnInstitucion(UUID usuarioId, UUID inst) {
        return usuarioRolRepository.findByUsuarioId(usuarioId).stream()
                .filter(ur -> inst.equals(ur.getInstitucionId()))
                .collect(Collectors.toList());
    }

    private Set<UUID> idsRolesStaff() {
        return ROLES_STAFF.stream()
                .map(rolRepository::findByCodigo)
                .flatMap(Optional::stream)
                .map(Rol::getId)
                .collect(Collectors.toSet());
    }

    private UUID idRol(String codigo) {
        return rolRepository.findByCodigo(codigo).map(Rol::getId).orElse(null);
    }

    private Usuario usuario(UUID usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe."));
    }

    private UUID institucionDe(UUID usuarioId) {
        return usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("El usuario no pertenece a ninguna institución."));
    }
}