package backend.staff.application.usecase;

import backend.onboarding.application.AltaUsuarioInstitucionService;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CrearUsuarioStaffUseCase {

    private static final Set<String> ROLES_STAFF =
            Set.of("RECTOR", "COORDINADOR_ACADEMICO", "SECRETARIA", "DOCENTE", "TESORERIA");

    private final AltaUsuarioInstitucionService altaUsuario;
    private final AutorizacionService autorizacionService;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;

    public CrearUsuarioStaffUseCase(AltaUsuarioInstitucionService altaUsuario,
                                    AutorizacionService autorizacionService,
                                    UsuarioInstitucionRepository usuarioInstitucionRepository) {
        this.altaUsuario = altaUsuario;
        this.autorizacionService = autorizacionService;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
    }

    @Transactional
    public Usuario ejecutar(UUID adminId, String rolCodigo, String tipoDocumento, String numeroDocumento,
                            String primerNombre, String primerApellido, String correo) {

        // 1. Rol asignable
        if (rolCodigo == null || !ROLES_STAFF.contains(rolCodigo)) {
            throw new RuntimeException("Rol no válido para el staff.");
        }

        // 2. Institución del admin (su vínculo principal) — no viene del body
        UUID institucionId = usuarioInstitucionRepository.findByUsuarioId(adminId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst()
                .map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("No perteneces a ninguna institución."));

        // 3. Debe ser ADMIN_INSTITUCION de esa institución
        autorizacionService.exigirRolEnInstitucion(adminId, institucionId, "ADMIN_INSTITUCION");

        // 4. Núcleo compartido
        return altaUsuario.provisionar(institucionId, rolCodigo, tipoDocumento, numeroDocumento,
                primerNombre, primerApellido, correo).usuario();
    }
}