package backend.staff.application.usecase;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.security.application.AutorizacionService;
import backend.security.application.port.RolRepository;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.application.port.UsuarioRepository;
import backend.security.application.port.UsuarioRolRepository;
import backend.security.domain.model.Rol;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.domain.model.UsuarioRol;
import backend.staff.application.StaffDetalle;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ObtenerStaffUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final RolRepository rolRepository;
    private final AutorizacionService autorizacionService;

    public ObtenerStaffUseCase(UsuarioRepository usuarioRepository, PersonaRepository personaRepository,
                               UsuarioRolRepository usuarioRolRepository,
                               UsuarioInstitucionRepository usuarioInstitucionRepository,
                               RolRepository rolRepository, AutorizacionService autorizacionService) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.autorizacionService = autorizacionService;
    }

    public StaffDetalle ejecutar(UUID adminId, UUID usuarioId) {
        UUID institucionAdmin = institucionDe(adminId);
        autorizacionService.exigirRolEnInstitucion(adminId, institucionAdmin, "ADMIN_INSTITUCION");

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe."));
        if (!institucionAdmin.equals(institucionDe(usuarioId))) {
            throw new SecurityException("No puedes ver usuarios de otra institución.");
        }
        Persona p = personaRepository.findById(usuario.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la persona."));
        Rol rol = rolDe(usuarioId, institucionAdmin);

        return new StaffDetalle(
                usuario.getId(), usuario.getUsername(), usuario.getEmailLogin(),
                usuario.getEstado() != null ? usuario.getEstado().name() : null,
                rol.getCodigo(), rol.getNombre(),
                p.getTipoDocumento() != null ? p.getTipoDocumento().name() : null, p.getNumeroDocumento(),
                p.getPrimerNombre(), p.getSegundoNombre(), p.getPrimerApellido(), p.getSegundoApellido(),
                p.getCorreo(), p.getTelefono(), p.getTelefonoAlternativo(),
                p.getFechaNacimiento(), p.getSexo() != null ? p.getSexo().name() : null, p.getNacionalidad(),
                p.getDireccion(), p.getBarrio(), p.getCiudad(), p.getDepartamento(), p.getPais(),
                p.getFotoUrl(), p.getObservaciones());
    }

    private UUID institucionDe(UUID usuarioId) {
        return usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("El usuario no pertenece a ninguna institución."));
    }

    private Rol rolDe(UUID usuarioId, UUID institucionId) {
        UUID rolId = usuarioRolRepository.findByUsuarioId(usuarioId).stream()
                .filter(ur -> institucionId.equals(ur.getInstitucionId()) && Boolean.TRUE.equals(ur.getActivo()))
                .map(UsuarioRol::getRolId).findFirst()
                .orElseThrow(() -> new RuntimeException("El usuario no tiene rol asignado."));
        return rolRepository.findById(rolId).orElseThrow(() -> new RuntimeException("Rol no encontrado."));
    }
}