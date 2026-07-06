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
import backend.security.domain.model.Rol;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
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

    public EditarAdministradorUseCase(UsuarioRepository usuarioRepository,
                                      PersonaRepository personaRepository,
                                      UsuarioRolRepository usuarioRolRepository,
                                      UsuarioInstitucionRepository usuarioInstitucionRepository,
                                      RolRepository rolRepository,
                                      InstitucionRepository institucionRepository,
                                      AutorizacionService autorizacionService) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.rolRepository = rolRepository;
        this.institucionRepository = institucionRepository;
        this.autorizacionService = autorizacionService;
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

        LocalDateTime ahora = LocalDateTime.now();

        // 1. Datos personales
        persona.setTipoDocumento(TipoDocumentoPersona.valueOf(tipoDocumento));
        persona.setNumeroDocumento(numeroDocumento);
        persona.setPrimerNombre(primerNombre);
        persona.setPrimerApellido(primerApellido);
        persona.setCorreo(correo);            // personal
        persona.setUpdatedAt(ahora);
        personaRepository.save(persona);

        // 2. Institución destino (validar)
        Institucion institucion = institucionRepository.findById(institucionId)
                .orElseThrow(() -> new RuntimeException("La institución indicada no existe."));
        if (institucion.getDominioCorreo() == null || institucion.getDominioCorreo().isBlank()) {
            throw new RuntimeException("La institución no tiene configurado el dominio de correo.");
        }

        // 3. Regenerar username (nombre/apellido/doc) y correo institucional (dominio)
        String nuevoUsername = generarUsername(primerNombre, primerApellido, numeroDocumento, usuario.getId());
        usuario.setUsername(nuevoUsername);
        usuario.setEmailLogin(nuevoUsername + "@" + institucion.getDominioCorreo());
        usuario.setUpdatedAt(ahora);
        usuarioRepository.save(usuario);
        // OJO: aquí NO se toca passwordHash ni requiereCambioPassword

        // 4. Si cambió de institución, mover vínculo principal + rol
        UsuarioInstitucion principal = usuarioInstitucionRepository.findByUsuarioId(usuarioId).stream()
                .filter(v -> Boolean.TRUE.equals(v.getEsPrincipal()))
                .findFirst().orElse(null);
        if (principal != null && !institucionId.equals(principal.getInstitucionId())) {
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

    private String generarUsername(String primerNombre, String primerApellido,
                                   String numeroDocumento, UUID usuarioId) {
        String nombre = normalizar(primerNombre);
        String apellido = normalizar(primerApellido);
        String soloDigitos = numeroDocumento.replaceAll("\\D", "");
        String dosDigitos = soloDigitos.length() >= 2 ? soloDigitos.substring(0, 2) : soloDigitos;

        String base = nombre + "-" + apellido + dosDigitos;
        String candidato = base;
        int intento = 1;
        while (true) {
            var existente = usuarioRepository.findByUsernameOrEmail(candidato);
            if (existente.isEmpty() || existente.get().getId().equals(usuarioId)) break; // libre o es él mismo
            candidato = base + "-" + intento;
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