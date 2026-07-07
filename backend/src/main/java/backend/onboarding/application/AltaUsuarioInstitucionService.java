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

        if (personaRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new RuntimeException("Ya existe una persona registrada con ese documento.");
        }

        Institucion institucion = institucionRepository.findById(institucionId)
                .orElseThrow(() -> new RuntimeException("La institución no existe."));
        if (institucion.getDominioCorreo() == null || institucion.getDominioCorreo().isBlank()) {
            throw new RuntimeException("La institución no tiene configurado el dominio de correo.");
        }

        Rol rol = rolRepository.findByCodigo(rolCodigo)
                .orElseThrow(() -> new RuntimeException("El rol " + rolCodigo + " no está configurado."));

        LocalDateTime ahora = LocalDateTime.now();

        // Persona (correo personal, no es credencial)
        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setTipoDocumento(TipoDocumentoPersona.valueOf(tipoDocumento));
        persona.setNumeroDocumento(numeroDocumento);
        persona.setPrimerNombre(primerNombre);
        persona.setPrimerApellido(primerApellido);
        persona.setCorreo(correoPersonal);
        persona.setPais("Colombia");
        persona.setCreatedAt(ahora);
        persona.setUpdatedAt(ahora);
        Persona personaGuardada = personaRepository.save(persona);

        // Usuario: username corto + correo institucional + contraseña = documento
        String username = usernameGenerator.generar(primerNombre, primerApellido, numeroDocumento);
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setPersonaId(personaGuardada.getId());
        usuario.setUsername(username);
        usuario.setEmailLogin(username + "@" + institucion.getDominioCorreo());
        usuario.setPasswordHash(passwordEncoder.encode(numeroDocumento));
        usuario.setTipoContexto(TipoContextoUsuario.INSTITUCION);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRequiereCambioPassword(true);
        usuario.setEmailVerificado(false);
        usuario.setDobleFactorHabilitado(false);
        usuario.setIntentosFallidos((short) 0);
        usuario.setCreatedAt(ahora);
        usuario.setUpdatedAt(ahora);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Rol ligado a la institución
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(UUID.randomUUID());
        usuarioRol.setUsuarioId(usuarioGuardado.getId());
        usuarioRol.setRolId(rol.getId());
        usuarioRol.setInstitucionId(institucionId);
        usuarioRol.setActivo(true);
        usuarioRol.setCreatedAt(ahora);
        usuarioRol.setUpdatedAt(ahora);
        usuarioRolRepository.save(usuarioRol);

        // Vínculo con la institución
        UsuarioInstitucion vinculo = new UsuarioInstitucion();
        vinculo.setId(UUID.randomUUID());
        vinculo.setUsuarioId(usuarioGuardado.getId());
        vinculo.setInstitucionId(institucionId);
        vinculo.setEsPrincipal(true);
        vinculo.setActivo(true);
        vinculo.setCreatedAt(ahora);
        vinculo.setUpdatedAt(ahora);
        usuarioInstitucionRepository.save(vinculo);

        return new Resultado(personaGuardada, usuarioGuardado);
    }
}