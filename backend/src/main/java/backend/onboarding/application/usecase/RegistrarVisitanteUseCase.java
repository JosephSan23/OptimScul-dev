package backend.onboarding.application.usecase;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.port.RolRepository;
import backend.security.application.port.UsuarioRepository;
import backend.security.application.port.UsuarioRolRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.Rol;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioRol;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrarVisitanteUseCase {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrarVisitanteUseCase(PersonaRepository personaRepository,
                                     UsuarioRepository usuarioRepository,
                                     UsuarioRolRepository usuarioRolRepository,
                                     RolRepository rolRepository,
                                     PasswordEncoder passwordEncoder) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario ejecutar(String tipoDocumento, String numeroDocumento, String primerNombre,
                            String primerApellido, String correo, String password) {

        // 1. Validaciones de duplicados
        if (personaRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new RuntimeException("Ya existe una persona registrada con ese documento.");
        }
        if (usuarioRepository.findByUsernameOrEmail(correo).isPresent()) {
            throw new RuntimeException("Ya existe una cuenta con ese correo.");
        }

        // 2. Buscar el rol VISITANTE
        Rol rolVisitante = rolRepository.findByCodigo("VISITANTE")
                .orElseThrow(() -> new RuntimeException("El rol VISITANTE no está configurado."));

        LocalDateTime ahora = LocalDateTime.now();

        // 3. Crear la persona
        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setTipoDocumento(TipoDocumentoPersona.valueOf(tipoDocumento));
        persona.setNumeroDocumento(numeroDocumento);
        persona.setPrimerNombre(primerNombre);
        persona.setPrimerApellido(primerApellido);
        persona.setCorreo(correo);
        persona.setPais("Colombia");
        persona.setCreatedAt(ahora);
        persona.setUpdatedAt(ahora);
        Persona personaGuardada = personaRepository.save(persona);

        // 4. Crear el usuario (username = parte antes del @, contraseña encriptada)
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setPersonaId(personaGuardada.getId());
        usuario.setUsername(generarUsername(correo));
        usuario.setPasswordHash(passwordEncoder.encode(password));
        usuario.setEmailLogin(correo);
        usuario.setTipoContexto(TipoContextoUsuario.PLATAFORMA);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRequiereCambioPassword(false);
        usuario.setEmailVerificado(false);
        usuario.setDobleFactorHabilitado(false);
        usuario.setIntentosFallidos((short) 0);
        usuario.setCreatedAt(ahora);
        usuario.setUpdatedAt(ahora);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 5. Asignar el rol VISITANTE
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(UUID.randomUUID());
        usuarioRol.setUsuarioId(usuarioGuardado.getId());
        usuarioRol.setRolId(rolVisitante.getId());
        usuarioRol.setInstitucionId(null);   // visitante no pertenece a institución
        usuarioRol.setActivo(true);
        usuarioRol.setCreatedAt(ahora);
        usuarioRol.setUpdatedAt(ahora);
        usuarioRolRepository.save(usuarioRol);

        return usuarioGuardado;
    }

    // Toma la parte antes del @ como username; si ya existe, le añade un sufijo
    private String generarUsername(String correo) {
        String base = correo.split("@")[0];
        String candidato = base;
        int intento = 1;
        while (usuarioRepository.findByUsernameOrEmail(candidato).isPresent()) {
            candidato = base + intento;
            intento++;
        }
        return candidato;
    }
}