package backend.staff.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.Persona;
import backend.people.domain.model.Sexo;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.AutorizacionService;
import backend.security.application.UsernameGenerator;
import backend.security.application.port.RolRepository;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.application.port.UsuarioRepository;
import backend.security.application.port.UsuarioRolRepository;
import backend.security.domain.model.Rol;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import backend.staff.infrastructure.rest.dto.EditarStaffRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class EditarStaffUseCase {

    private static final Set<String> ROLES_STAFF =
            Set.of("RECTOR", "COORDINADOR_ACADEMICO", "SECRETARIA", "DOCENTE", "TESORERIA");

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final RolRepository rolRepository;
    private final InstitucionRepository institucionRepository;
    private final AutorizacionService autorizacionService;
    private final UsernameGenerator usernameGenerator;

    public EditarStaffUseCase(UsuarioRepository usuarioRepository, PersonaRepository personaRepository,
                              UsuarioRolRepository usuarioRolRepository,
                              UsuarioInstitucionRepository usuarioInstitucionRepository,
                              RolRepository rolRepository, InstitucionRepository institucionRepository,
                              AutorizacionService autorizacionService, UsernameGenerator usernameGenerator) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.institucionRepository = institucionRepository;
        this.autorizacionService = autorizacionService;
        this.usernameGenerator = usernameGenerator;
    }

    @Transactional
    public void ejecutar(UUID adminId, UUID usuarioId, EditarStaffRequestDto dto) {
        UUID institucionAdmin = institucionDe(adminId);
        autorizacionService.exigirRolEnInstitucion(adminId, institucionAdmin, "ADMIN_INSTITUCION");

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe."));
        if (!institucionAdmin.equals(institucionDe(usuarioId))) {
            throw new SecurityException("No puedes editar usuarios de otra institución.");
        }
        if (!ROLES_STAFF.contains(dto.getRolCodigo())) {
            throw new RuntimeException("Rol no válido para el staff.");
        }

        Persona p = personaRepository.findById(usuario.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la persona."));
        if (!p.getNumeroDocumento().equals(dto.getNumeroDocumento())
                && personaRepository.existsByNumeroDocumento(dto.getNumeroDocumento())) {
            throw new RuntimeException("Ya existe otra persona con ese documento.");
        }

        LocalDateTime ahora = LocalDateTime.now();

        // Datos personales (la foto NO se toca aquí; va por el endpoint de subida)
        p.setTipoDocumento(TipoDocumentoPersona.valueOf(dto.getTipoDocumento()));
        p.setNumeroDocumento(dto.getNumeroDocumento());
        p.setPrimerNombre(dto.getPrimerNombre());
        p.setSegundoNombre(dto.getSegundoNombre());
        p.setPrimerApellido(dto.getPrimerApellido());
        p.setSegundoApellido(dto.getSegundoApellido());
        p.setCorreo(dto.getCorreo());
        p.setTelefono(dto.getTelefono());
        p.setTelefonoAlternativo(dto.getTelefonoAlternativo());
        p.setFechaNacimiento(dto.getFechaNacimiento());
        if (dto.getSexo() != null && !dto.getSexo().isBlank()) p.setSexo(Sexo.valueOf(dto.getSexo()));
        p.setNacionalidad(dto.getNacionalidad());
        p.setDireccion(dto.getDireccion());
        p.setBarrio(dto.getBarrio());
        p.setCiudad(dto.getCiudad());
        p.setDepartamento(dto.getDepartamento());
        p.setPais(dto.getPais());
        p.setObservaciones(dto.getObservaciones());
        p.setUpdatedAt(ahora);
        personaRepository.save(p);

        // Regenerar username + correo institucional (el nombre pudo cambiar)
        Institucion inst = institucionRepository.findById(institucionAdmin)
                .orElseThrow(() -> new RuntimeException("La institución no existe."));
        String username = usernameGenerator.generar(dto.getPrimerNombre(), dto.getPrimerApellido(),
                dto.getNumeroDocumento(), usuarioId);
        usuario.setUsername(username);
        usuario.setEmailLogin(username + "@" + inst.getDominioCorreo());
        usuario.setUpdatedAt(ahora);
        usuarioRepository.save(usuario);
        // NO se toca passwordHash → la contraseña se conserva

        // Cambio de rol (dentro del staff)
        Rol nuevoRol = rolRepository.findByCodigo(dto.getRolCodigo())
                .orElseThrow(() -> new RuntimeException("El rol " + dto.getRolCodigo() + " no está configurado."));
        usuarioRolRepository.findByUsuarioId(usuarioId).stream()
                .filter(ur -> institucionAdmin.equals(ur.getInstitucionId()))
                .forEach(ur -> {
                    ur.setRolId(nuevoRol.getId());
                    ur.setUpdatedAt(ahora);
                    usuarioRolRepository.save(ur);
                });
    }

    private UUID institucionDe(UUID usuarioId) {
        return usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().map(UsuarioInstitucion::getInstitucionId)
                .orElseThrow(() -> new RuntimeException("El usuario no pertenece a ninguna institución."));
    }
}