package backend.onboarding.application;

import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.UsernameGenerator;
import backend.security.application.port.RolRepository;
import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.application.port.UsuarioRepository;
import backend.security.application.port.UsuarioRolRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.Rol;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import backend.security.domain.model.UsuarioRol;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AltaUsuarioInstitucionService {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final RolRepository rolRepository;
    private final InstitucionRepository institucionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsernameGenerator usernameGenerator;

    public AltaUsuarioInstitucionService(PersonaRepository personaRepository,
                                         UsuarioRepository usuarioRepository,
                                         UsuarioRolRepository usuarioRolRepository,
                                         UsuarioInstitucionRepository usuarioInstitucionRepository,
                                         RolRepository rolRepository,
                                         InstitucionRepository institucionRepository,
                                         PasswordEncoder passwordEncoder,
                                         UsernameGenerator usernameGenerator) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.institucionRepository = institucionRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameGenerator = usernameGenerator;
    }

    public record Resultado(Persona persona, Usuario usuario) {}

    /**
     * Núcleo reutilizable: persona + usuario + rol + vínculo con la institución.
     * Llamar dentro de una transacción (el caso de uso pone @Transactional).
     */
    public Resultado provisionar(UUID institucionId, String rolCodigo,
                                 String tipoDocumento, String numeroDocumento,
                                 String primerNombre, String primerApellido, String correoPersonal) {

        Institucion institucion = institucionRepository.findById(institucionId)
                .orElseThrow(() -> new RuntimeException("La institución no existe."));
        if (institucion.getDominioCorreo() == null || institucion.getDominioCorreo().isBlank()) {
            throw new RuntimeException("La institución no tiene configurado el dominio de correo.");
        }

        Rol rol = rolRepository.findByCodigo(rolCodigo)
                .orElseThrow(() -> new RuntimeException("El rol " + rolCodigo + " no está configurado."));

        LocalDateTime ahora = LocalDateTime.now();

        // ── 1. Persona: reutilizar si ya existe (doble rol), crear si no ──
        Persona persona = personaRepository.findByNumeroDocumento(numeroDocumento)
                .orElseGet(() -> {
                    Persona p = new Persona();
                    p.setId(UUID.randomUUID());
                    p.setTipoDocumento(TipoDocumentoPersona.valueOf(tipoDocumento));
                    p.setNumeroDocumento(numeroDocumento);
                    p.setPrimerNombre(primerNombre);
                    p.setPrimerApellido(primerApellido);
                    p.setCorreo(correoPersonal);
                    p.setPais("Colombia");
                    p.setCreatedAt(ahora);
                    p.setUpdatedAt(ahora);
                    return personaRepository.save(p);
                });

        // ── 2. Usuario: reutilizar la cuenta si la persona ya tiene una ──
        Usuario usuario = usuarioRepository.findByPersonaId(persona.getId())
                .orElseGet(() -> {
                    String username = usernameGenerator.generar(primerNombre, primerApellido, numeroDocumento);
                    Usuario u = new Usuario();
                    u.setId(UUID.randomUUID());
                    u.setPersonaId(persona.getId());
                    u.setUsername(username);
                    u.setEmailLogin(username + "@" + institucion.getDominioCorreo());
                    u.setPasswordHash(passwordEncoder.encode(numeroDocumento));
                    u.setTipoContexto(TipoContextoUsuario.INSTITUCION);
                    u.setEstado(EstadoUsuario.ACTIVO);
                    u.setRequiereCambioPassword(true);
                    u.setEmailVerificado(false);
                    u.setDobleFactorHabilitado(false);
                    u.setIntentosFallidos((short) 0);
                    u.setCreatedAt(ahora);
                    u.setUpdatedAt(ahora);
                    return usuarioRepository.save(u);
                });

        // ── 3. Rol: agregar SIN tocar los que ya tenga (aquí vive el doble rol) ──
        boolean yaTieneRol = usuarioRolRepository.findByUsuarioId(usuario.getId()).stream()
                .anyMatch(ur -> institucionId.equals(ur.getInstitucionId())
                             && rol.getId().equals(ur.getRolId())
                             && Boolean.TRUE.equals(ur.getActivo()));
        if (yaTieneRol) {
            throw new RuntimeException("Esa persona ya tiene el rol " + rolCodigo + " en esta institución.");
        }
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(UUID.randomUUID());
        usuarioRol.setUsuarioId(usuario.getId());
        usuarioRol.setRolId(rol.getId());
        usuarioRol.setInstitucionId(institucionId);
        usuarioRol.setActivo(true);
        usuarioRol.setCreatedAt(ahora);
        usuarioRol.setUpdatedAt(ahora);
        usuarioRolRepository.save(usuarioRol);

        // ── 4. Vínculo con la institución: solo si no existe ya ──
        boolean yaVinculado = usuarioInstitucionRepository.findByUsuarioId(usuario.getId()).stream()
                .anyMatch(v -> institucionId.equals(v.getInstitucionId()));
        if (!yaVinculado) {
            boolean tienePrincipal = !usuarioInstitucionRepository.findByUsuarioId(usuario.getId()).isEmpty();
            UsuarioInstitucion vinculo = new UsuarioInstitucion();
            vinculo.setId(UUID.randomUUID());
            vinculo.setUsuarioId(usuario.getId());
            vinculo.setInstitucionId(institucionId);
            vinculo.setEsPrincipal(!tienePrincipal);   // principal solo si es su primera institución
            vinculo.setActivo(true);
            vinculo.setCreatedAt(ahora);
            vinculo.setUpdatedAt(ahora);
            usuarioInstitucionRepository.save(vinculo);
        }

        return new Resultado(persona, usuario);
    }
}