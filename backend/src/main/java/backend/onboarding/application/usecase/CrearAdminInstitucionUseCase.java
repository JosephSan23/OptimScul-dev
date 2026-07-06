package backend.onboarding.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.AutorizacionService;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CrearAdminInstitucionUseCase {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final RolRepository rolRepository;
    private final InstitucionRepository institucionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AutorizacionService autorizacionService;

    public CrearAdminInstitucionUseCase(PersonaRepository personaRepository,
                                        UsuarioRepository usuarioRepository,
                                        UsuarioRolRepository usuarioRolRepository,
                                        UsuarioInstitucionRepository usuarioInstitucionRepository,
                                        RolRepository rolRepository,
                                        InstitucionRepository institucionRepository,
                                        PasswordEncoder passwordEncoder,
                                        AutorizacionService autorizacionService) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.institucionRepository = institucionRepository;
        this.passwordEncoder = passwordEncoder;
        this.autorizacionService = autorizacionService;
    }

    @Transactional
    public Usuario ejecutar(UUID superAdminId, String tipoDocumento, String numeroDocumento,
                            String primerNombre, String primerApellido, String correo, UUID institucionId) {

        // 0. Solo el super admin
        autorizacionService.exigirSuperAdmin(superAdminId);

        // 1. Duplicado por documento
        if (personaRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new RuntimeException("Ya existe una persona registrada con ese documento.");
        }

        // 2. La institución debe existir y tener dominio configurado
        Institucion institucion = institucionRepository.findById(institucionId)
                .orElseThrow(() -> new RuntimeException("La institución indicada no existe."));
        if (institucion.getDominioCorreo() == null || institucion.getDominioCorreo().isBlank()) {
            throw new RuntimeException("La institución no tiene configurado el dominio de correo.");
        }

        // 3. Rol
        Rol rolAdmin = rolRepository.findByCodigo("ADMIN_INSTITUCION")
                .orElseThrow(() -> new RuntimeException("El rol ADMIN_INSTITUCION no está configurado."));

        LocalDateTime ahora = LocalDateTime.now();

        // 4. Persona (el correo PERSONAL vive aquí; NO es credencial de login)
        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setTipoDocumento(TipoDocumentoPersona.valueOf(tipoDocumento));
        persona.setNumeroDocumento(numeroDocumento);
        persona.setPrimerNombre(primerNombre);
        persona.setPrimerApellido(primerApellido);
        persona.setCorreo(correo);                 // correo personal → para notificaciones futuras
        persona.setPais("Colombia");
        persona.setCreatedAt(ahora);
        persona.setUpdatedAt(ahora);
        Persona personaGuardada = personaRepository.save(persona);

        // 5. Usuario: username corto + correo INSTITUCIONAL como login
        String username = generarUsername(primerNombre, primerApellido, numeroDocumento);
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setPersonaId(personaGuardada.getId());
        usuario.setUsername(username);
        usuario.setEmailLogin(username + "@" + institucion.getDominioCorreo());   // institucional
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

        // 6. Rol ligado a la institución
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(UUID.randomUUID());
        usuarioRol.setUsuarioId(usuarioGuardado.getId());
        usuarioRol.setRolId(rolAdmin.getId());
        usuarioRol.setInstitucionId(institucionId);
        usuarioRol.setActivo(true);
        usuarioRol.setCreatedAt(ahora);
        usuarioRol.setUpdatedAt(ahora);
        usuarioRolRepository.save(usuarioRol);

        // 7. Vínculo con institución
        UsuarioInstitucion vinculo = new UsuarioInstitucion();
        vinculo.setId(UUID.randomUUID());
        vinculo.setUsuarioId(usuarioGuardado.getId());
        vinculo.setInstitucionId(institucionId);
        vinculo.setEsPrincipal(true);
        vinculo.setActivo(true);
        vinculo.setCreatedAt(ahora);
        vinculo.setUpdatedAt(ahora);
        usuarioInstitucionRepository.save(vinculo);

        return usuarioGuardado;
    }

    // Username corto y único: nombre-apellido + 2 primeros dígitos del documento
    private String generarUsername(String primerNombre, String primerApellido, String numeroDocumento) {
        String nombre = normalizar(primerNombre);
        String apellido = normalizar(primerApellido);
        String soloDigitos = numeroDocumento.replaceAll("\\D", "");
        String dosDigitos = soloDigitos.length() >= 2 ? soloDigitos.substring(0, 2) : soloDigitos;

        String base = nombre + "-" + apellido + dosDigitos;
        String candidato = base;
        int intento = 1;
        while (usuarioRepository.findByUsernameOrEmail(candidato).isPresent()) {
            candidato = base + intento;
            intento++;
        }
        return candidato;
    }

    private String normalizar(String texto) {
        String sinTildes = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase().replaceAll("[^a-z0-9]", "");
    }
}