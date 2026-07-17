package backend.staff.application.usecase;

import backend.onboarding.application.AltaUsuarioInstitucionService;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.application.port.UsuarioRepository;
import backend.people.application.port.PersonaRepository;
import backend.onboarding.application.AltaUsuarioInstitucionService.Resultado;
import backend.people.application.PerfilProfesorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.List;

@Service
public class CrearUsuarioStaffUseCase {

    private static final Set<String> ROLES_STAFF =
            Set.of("RECTOR", "COORDINADOR_ACADEMICO", "SECRETARIA", "DOCENTE", "TESORERIA");

    private final AltaUsuarioInstitucionService altaUsuario;
    private final AutorizacionService autorizacionService;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final PerfilProfesorService perfilProfesorService;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;

    public CrearUsuarioStaffUseCase(AltaUsuarioInstitucionService altaUsuario,
                                    AutorizacionService autorizacionService,
                                    UsuarioInstitucionRepository usuarioInstitucionRepository,
                                    PerfilProfesorService perfilProfesorService,
                                    PersonaRepository personaRepository,
                                    UsuarioRepository usuarioRepository) {
        this.altaUsuario = altaUsuario;
        this.autorizacionService = autorizacionService;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.perfilProfesorService = perfilProfesorService;
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario ejecutar(UUID adminId, String rolCodigo, String tipoDocumento, String numeroDocumento,
                            String primerNombre, String primerApellido, String correo) {

        // 1. Rol asignable
        if (rolCodigo == null || !ROLES_STAFF.contains(rolCodigo)) {
            throw new RuntimeException("Rol no válido para el staff.");
        }

        numeroDocumento = numeroDocumento == null ? null : numeroDocumento.trim();   // ← NUEVA

        // 2. Institución del admin (su vínculo principal) — no viene del body
        UUID institucionId = usuarioInstitucionRepository.findByUsuarioId(adminId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst()
                .map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("No perteneces a ninguna institución."));

        // 3. Debe ser ADMIN_INSTITUCION de esa institución
        autorizacionService.exigirRolEnInstitucion(adminId, institucionId, "ADMIN_INSTITUCION");

        // 3.5 — Un solo cargo staff por institución: si la persona ya es staff aquí,
        //       el cambio de cargo se hace por EDITAR, no creando de nuevo.
        personaRepository.findByNumeroDocumento(numeroDocumento)
                .flatMap(p -> usuarioRepository.findByPersonaId(p.getId()))
                .ifPresent(u -> {
                    List<String> rolesEnInstitucion =
                            usuarioRepository.findRolesByUsuarioIdAndInstitucion(u.getId(), institucionId);
                    rolesEnInstitucion.stream()
                            .filter(ROLES_STAFF::contains)
                            .findFirst()
                            .ifPresent(cargo -> {
                                throw new RuntimeException("Esa persona ya tiene el cargo " + cargo
                                        + " en esta institución. Usa la opción de editar para cambiarle el cargo.");
                            });
                });

        // 4. Núcleo compartido
        Resultado r = altaUsuario.provisionar(institucionId, rolCodigo, tipoDocumento, numeroDocumento,
                primerNombre, primerApellido, correo);

        // 5. Si es docente, crear su perfil académico (tabla profesor)
        if ("DOCENTE".equals(rolCodigo)) {
            perfilProfesorService.asegurarPerfil(institucionId, r.persona().getId(), numeroDocumento);
        }

        return r.usuario();
    }
}