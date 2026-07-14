package backend.onboarding.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.Persona;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarAdministradorUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final RolRepository rolRepository;
    private final InstitucionRepository institucionRepository;
    private final AutorizacionService autorizacionService;
    private final UsernameGenerator usernameGenerator;
    private final PasswordEncoder passwordEncoder;

    public EditarAdministradorUseCase(UsuarioRepository usuarioRepository,
                                      PersonaRepository personaRepository,
                                      UsuarioRolRepository usuarioRolRepository,
                                      UsuarioInstitucionRepository usuarioInstitucionRepository,
                                      RolRepository rolRepository,
                                      InstitucionRepository institucionRepository,
                                      AutorizacionService autorizacionService,
                                      UsernameGenerator usernameGenerator,
                                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.institucionRepository = institucionRepository;
        this.autorizacionService = autorizacionService;
        this.usernameGenerator = usernameGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void ejecutar(UUID superAdminId, UUID usuarioId, String tipoDocumento, String numeroDocumento,
                         String primerNombre, String primerApellido, String correo, UUID institucionId) {

        autorizacionService.exigirSuperAdmin(superAdminId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El administrador no existe."));
        Persona persona = personaRepository.findById(usuario.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la persona del administrador."));

        // Si el documento cambia, no debe chocar con OTRA persona
        if (!persona.getNumeroDocumento().equals(numeroDocumento)
                && personaRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new RuntimeException("Ya existe otra persona con ese documento.");
        }

        boolean documentoCambio = !persona.getNumeroDocumento().equals(numeroDocumento);
        LocalDateTime ahora = LocalDateTime.now();

        // 1. Datos personales
        persona.setTipoDocumento(TipoDocumentoPersona.valueOf(tipoDocumento));
        persona.setNumeroDocumento(numeroDocumento);
        persona.setPrimerNombre(primerNombre);
        persona.setPrimerApellido(primerApellido);
        persona.setCorreo(correo); // personal
        persona.setUpdatedAt(ahora);
        personaRepository.save(persona);

        // 2. Institución destino (validar)
        Institucion institucion = institucionRepository.findById(institucionId)
                .orElseThrow(() -> new RuntimeException("La institución indicada no existe."));
        if (institucion.getDominioCorreo() == null || institucion.getDominioCorreo().isBlank()) {
            throw new RuntimeException("La institución no tiene configurado el dominio de correo.");
        }

        UsuarioInstitucion principal = usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().orElse(null);
        boolean cambioInstitucion = principal != null && !institucionId.equals(principal.getInstitucionId());

        // 3. Username y password: solo se regeneran/resetean si el admin NUNCA ha iniciado sesión.
        //    Congelados después del primer login para no romper el acceso ya usado.
        if (usuario.getUltimoLogin() == null) {
            String nuevoUsername = usernameGenerator.generar(primerNombre, primerApellido,
                    numeroDocumento, usuario.getId());
            usuario.setUsername(nuevoUsername);

            if (documentoCambio) {
                usuario.setPasswordHash(passwordEncoder.encode(numeroDocumento));
                usuario.setRequiereCambioPassword(true);
            }
        }

        // 4. El dominio del emailLogin SIEMPRE debe reflejar la institución actual,
        //    incluso si ya inició sesión: si lo movieron de institución, su correo
        //    institucional tiene que corresponder a la institución a la que pertenece ahora.
        if (cambioInstitucion || usuario.getUltimoLogin() == null) {
            usuario.setEmailLogin(usuario.getUsername() + "@" + institucion.getDominioCorreo());
        }

        usuario.setUpdatedAt(ahora);
        usuarioRepository.save(usuario);

        // 5. Si cambió de institución, mover vínculo principal + rol
        if (cambioInstitucion) {
            principal.setInstitucionId(institucionId);
            principal.setUpdatedAt(ahora);
            usuarioInstitucionRepository.save(principal);

            Rol rolAdmin = rolRepository.findByCodigo("ADMIN_INSTITUCION")
                    .orElseThrow(() -> new RuntimeException("El rol ADMIN_INSTITUCION no está configurado."));
            usuarioRolRepository.findByUsuarioId(usuarioId).stream()
                    .filter(ur -> rolAdmin.getId().equals(ur.getRolId()))
                    .forEach(ur -> {
                        ur.setInstitucionId(institucionId);
                        ur.setUpdatedAt(ahora);
                        usuarioRolRepository.save(ur);
                    });
        }
    }
}