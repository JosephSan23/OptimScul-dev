package backend.onboarding.application.usecase;

import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
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
import backend.people.domain.model.Institucion;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class CrearAdminInstitucionUseCase {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final RolRepository rolRepository;
    private final InstitucionRepository institucionRepository;
    private final PasswordEncoder passwordEncoder;

    public CrearAdminInstitucionUseCase(PersonaRepository personaRepository,
                                        UsuarioRepository usuarioRepository,
                                        UsuarioRolRepository usuarioRolRepository,
                                        UsuarioInstitucionRepository usuarioInstitucionRepository,
                                        RolRepository rolRepository,
                                        InstitucionRepository institucionRepository,
                                        PasswordEncoder passwordEncoder) {
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.institucionRepository = institucionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario ejecutar(UUID superAdminId, String tipoDocumento, String numeroDocumento,
                            String primerNombre, String primerApellido, String correo, UUID institucionId) {

        // 1. Validaciones de duplicados
        if (personaRepository.existsByNumeroDocumento(numeroDocumento)) {
            throw new RuntimeException("Ya existe una persona registrada con ese documento.");
        }
        if (usuarioRepository.findByUsernameOrEmail(correo).isPresent()) {
            throw new RuntimeException("Ya existe una cuenta con ese correo.");
        }

        // 0. Solo el super admin puede crear administradores (PLATAFORMA y sin rol VISITANTE)
        Usuario solicitante = usuarioRepository.findById(superAdminId)
                .orElseThrow(() -> new SecurityException("Usuario no autorizado."));
        List<String> rolesSolicitante = usuarioRepository.findRolesByUsuarioId(superAdminId);
        boolean esSuperAdmin = solicitante.getTipoContexto() == TipoContextoUsuario.PLATAFORMA
                && !rolesSolicitante.contains("VISITANTE");
        if (!esSuperAdmin) {
            throw new SecurityException("Solo el super administrador puede crear administradores de institución.");
        }

        // 2. La institución debe existir y tener dominio de correo configurado
        Institucion institucion = institucionRepository.findById(institucionId)
                .orElseThrow(() -> new RuntimeException("La institución indicada no existe."));
        if (institucion.getDominioCorreo() == null || institucion.getDominioCorreo().isBlank()) {
            throw new RuntimeException("La institución no tiene configurado el dominio de correo.");
        }

        // 3. Buscar el rol ADMIN_INSTITUCION
        Rol rolAdmin = rolRepository.findByCodigo("ADMIN_INSTITUCION")
                .orElseThrow(() -> new RuntimeException("El rol ADMIN_INSTITUCION no está configurado."));

        LocalDateTime ahora = LocalDateTime.now();

        // 4. Crear la persona
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

        // 5. Crear el usuario (contraseña = número de documento, contexto INSTITUCION, nace ACTIVO)
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setPersonaId(personaGuardada.getId());
        // antes:  usuario.setUsername(generarUsername(correo));
        usuario.setUsername(generarUsername(primerNombre, primerApellido, numeroDocumento,
                                            institucion.getDominioCorreo()));
        usuario.setPasswordHash(passwordEncoder.encode(numeroDocumento));
        usuario.setEmailLogin(correo);
        usuario.setTipoContexto(TipoContextoUsuario.INSTITUCION);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRequiereCambioPassword(true);   // password = documento -> forzar cambio al primer login
        usuario.setEmailVerificado(false);
        usuario.setDobleFactorHabilitado(false);
        usuario.setIntentosFallidos((short) 0);
        usuario.setCreatedAt(ahora);
        usuario.setUpdatedAt(ahora);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 6. Asignar el rol ADMIN_INSTITUCION (ligado a la institución)
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(UUID.randomUUID());
        usuarioRol.setUsuarioId(usuarioGuardado.getId());
        usuarioRol.setRolId(rolAdmin.getId());
        usuarioRol.setInstitucionId(institucionId);   // el admin SÍ pertenece a institución
        usuarioRol.setActivo(true);
        usuarioRol.setCreatedAt(ahora);
        usuarioRol.setUpdatedAt(ahora);
        usuarioRolRepository.save(usuarioRol);

        // 7. Vincular el usuario con la institución (tabla usuario_institucion)
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

    // Formato: primernombre-primerapellido + 2 primeros dígitos del documento @dominio
    private String generarUsername(String primerNombre, String primerApellido,
                                   String numeroDocumento, String dominio) {
        String nombre = normalizar(primerNombre);
        String apellido = normalizar(primerApellido);
        String soloDigitos = numeroDocumento.replaceAll("\\D", "");
        String dosDigitos = soloDigitos.length() >= 2 ? soloDigitos.substring(0, 2) : soloDigitos;

        String local = nombre + "-" + apellido + dosDigitos;
        String candidato = local + "@" + dominio;

        // Si ya existe, añade un sufijo antes de la @ (ej. ana-ruiz12-1@...)
        int intento = 1;
        while (usuarioRepository.findByUsernameOrEmail(candidato).isPresent()) {
            candidato = local + "-" + intento + "@" + dominio;
            intento++;
        }
        return candidato;
    }

    // minúsculas, sin tildes y solo letras/números
    private String normalizar(String texto) {
        String sinTildes = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase().replaceAll("[^a-z0-9]", "");
    }
}