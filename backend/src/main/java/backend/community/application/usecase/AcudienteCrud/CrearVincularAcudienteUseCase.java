package backend.community.application.usecase.AcudienteCrud;

import backend.community.infrastructure.rest.dto.AcudienteRequestDto;
import backend.onboarding.application.AltaUsuarioInstitucionService;
import backend.people.application.port.*;
import backend.people.domain.model.*;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CrearVincularAcudienteUseCase {

    private final AltaUsuarioInstitucionService altaUsuario;
    private final EstudianteRepository estudianteRepository;
    private final PersonaRepository personaRepository;
    private final AcudienteRepository acudienteRepository;
    private final EstudianteAcudienteRepository vinculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AutorizacionService auth;

    public CrearVincularAcudienteUseCase(AltaUsuarioInstitucionService altaUsuario,
            EstudianteRepository estudianteRepository,
            PersonaRepository personaRepository, AcudienteRepository acudienteRepository,
            EstudianteAcudienteRepository vinculoRepository,
            UsuarioRepository usuarioRepository, AutorizacionService auth) {
        this.altaUsuario = altaUsuario;
        this.estudianteRepository = estudianteRepository;
        this.personaRepository = personaRepository;
        this.acudienteRepository = acudienteRepository;
        this.vinculoRepository = vinculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.auth = auth;
    }

    /**
     * CUENTA_CREADA     → se le habilitó una cuenta de acceso (es principal y no tenía).
     * CUENTA_REUTILIZADA→ ya tenía cuenta (mismo principal para varios estudiantes, o doble rol).
     * SOLO_CONTACTO     → acudiente de contacto, SIN cuenta de acceso (no es principal).
     */
    public enum Tipo {
        CUENTA_CREADA, CUENTA_REUTILIZADA, SOLO_CONTACTO
    }

    public record Resultado(Tipo tipo, String username) {
    }

    @Transactional
    public Resultado ejecutar(UUID coordId, UUID estudianteId, AcudienteRequestDto dto) {
        String numeroDocumento = dto.getNumeroDocumento() == null ? null : dto.getNumeroDocumento().trim();
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        Estudiante est = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(est.getInstitucionId()))
            throw new SecurityException("El estudiante es de otra institución.");

        // Máximo 3 acudientes por estudiante.
        var vinculosExistentes = vinculoRepository.findByEstudianteId(estudianteId);
        if (vinculosExistentes.size() >= 3) {
            throw new RuntimeException("Un estudiante puede tener máximo 3 acudientes.");
        }

        // Solo el acudiente PRINCIPAL tiene cuenta de acceso; los demás son contacto.
        boolean esPrincipal = dto.getEsPrincipal() != null && dto.getEsPrincipal();
        // Siempre debe existir un principal: si el estudiante aún no tiene ninguno,
        // este acudiente pasa a ser principal por defecto.
        boolean hayPrincipal = vinculosExistentes.stream()
                .anyMatch(l -> Boolean.TRUE.equals(l.getEsPrincipal()));
        if (!hayPrincipal) {
            esPrincipal = true;
        }
        LocalDateTime ahora = LocalDateTime.now();

        Optional<Persona> personaExistente = personaRepository.findByNumeroDocumento(numeroDocumento);
        Acudiente acudiente;
        Tipo tipo;
        String username = null;

        if (personaExistente.isEmpty()) {
            // ── Persona totalmente nueva ──
            if (esPrincipal) {
                // Principal → cuenta completa (persona + usuario ACUDIENTE + rol + vínculo institución)
                AltaUsuarioInstitucionService.Resultado cuenta = altaUsuario.provisionar(
                        inst, "ACUDIENTE", dto.getTipoDocumento(), numeroDocumento,
                        dto.getPrimerNombre(), dto.getPrimerApellido(), dto.getCorreo());
                username = cuenta.usuario().getUsername();
                acudiente = crearRegistroAcudiente(inst, cuenta.persona().getId(), dto, ahora);
                tipo = Tipo.CUENTA_CREADA;
            } else {
                // Solo contacto → se crea la persona SIN cuenta ni rol
                Persona persona = crearPersonaContacto(numeroDocumento, dto, ahora);
                acudiente = crearRegistroAcudiente(inst, persona.getId(), dto, ahora);
                tipo = Tipo.SOLO_CONTACTO;
            }

        } else {
            Persona persona = personaExistente.get();
            Acudiente existente = acudienteRepository.findByPersonaId(persona.getId()).orElse(null);
            boolean tieneCuenta = usuarioRepository.findByPersonaId(persona.getId()).isPresent();

            if (existente != null) {
                // ── Ya es acudiente (hermanos: mismo acudiente para otro estudiante) ──
                if (!inst.equals(existente.getInstitucionId()))
                    throw new RuntimeException("Ese acudiente pertenece a otra institución.");
                acudiente = existente;

                if (esPrincipal && !tieneCuenta) {
                    // Era solo contacto y ahora lo marcan principal → se le habilita la cuenta
                    AltaUsuarioInstitucionService.Resultado cuenta = altaUsuario.provisionar(
                            inst, "ACUDIENTE", dto.getTipoDocumento(), numeroDocumento,
                            dto.getPrimerNombre(), dto.getPrimerApellido(), dto.getCorreo());
                    username = cuenta.usuario().getUsername();
                    tipo = Tipo.CUENTA_CREADA;
                } else {
                    // Ya tenía cuenta (mismo principal para 2 estudiantes) o sigue como contacto
                    tipo = tieneCuenta ? Tipo.CUENTA_REUTILIZADA : Tipo.SOLO_CONTACTO;
                }

            } else {
                // ── La persona existe (staff, docente...) pero aún no es acudiente ──
                if (esPrincipal) {
                    AltaUsuarioInstitucionService.Resultado cuenta = altaUsuario.provisionar(
                            inst, "ACUDIENTE", dto.getTipoDocumento(), numeroDocumento,
                            dto.getPrimerNombre(), dto.getPrimerApellido(), dto.getCorreo());
                    username = cuenta.usuario().getUsername();
                    tipo = tieneCuenta ? Tipo.CUENTA_REUTILIZADA : Tipo.CUENTA_CREADA;
                    acudiente = crearRegistroAcudiente(inst, persona.getId(), dto, ahora);
                } else {
                    // Solo contacto, reutilizando la persona existente (sin cuenta)
                    acudiente = crearRegistroAcudiente(inst, persona.getId(), dto, ahora);
                    tipo = Tipo.SOLO_CONTACTO;
                }
            }
        }

        // Evitar vínculo duplicado
        if (vinculoRepository.existsByEstudianteIdAndAcudienteId(estudianteId, acudiente.getId())) {
            throw new RuntimeException("Ese acudiente ya está vinculado a este estudiante.");
        }

        // Un solo principal por estudiante: si este entra como principal, los demás pasan a contacto.
        if (esPrincipal) {
            for (EstudianteAcudiente otro : vinculoRepository.findByEstudianteId(estudianteId)) {
                if (Boolean.TRUE.equals(otro.getEsPrincipal())) {
                    otro.setEsPrincipal(false);
                    otro.setUpdatedAt(ahora);
                    vinculoRepository.save(otro);
                }
            }
        }

        EstudianteAcudiente v = new EstudianteAcudiente();
        v.setId(UUID.randomUUID());
        v.setEstudianteId(estudianteId);
        v.setAcudienteId(acudiente.getId());
        v.setParentesco(dto.getParentesco());
        v.setEsPrincipal(esPrincipal);
        v.setAutorizadoRecogida(dto.getAutorizadoRecogida() != null && dto.getAutorizadoRecogida());
        // El acceso a la información académica va ligado a ser principal.
        v.setAutorizadoInfoAcademica(esPrincipal);
        v.setCreatedAt(ahora);
        v.setUpdatedAt(ahora);
        vinculoRepository.save(v);

        return new Resultado(tipo, username);
    }

    private Persona crearPersonaContacto(String numeroDocumento, AcudienteRequestDto dto, LocalDateTime ahora) {
        Persona p = new Persona();
        p.setId(UUID.randomUUID());
        p.setTipoDocumento(TipoDocumentoPersona.valueOf(dto.getTipoDocumento()));
        p.setNumeroDocumento(numeroDocumento);
        p.setPrimerNombre(dto.getPrimerNombre());
        p.setPrimerApellido(dto.getPrimerApellido());
        p.setCorreo(dto.getCorreo());
        p.setPais("Colombia");
        p.setCreatedAt(ahora);
        p.setUpdatedAt(ahora);
        return personaRepository.save(p);
    }

    private Acudiente crearRegistroAcudiente(UUID inst, UUID personaId, AcudienteRequestDto dto, LocalDateTime ahora) {
        Acudiente nuevo = new Acudiente();
        nuevo.setId(UUID.randomUUID());
        nuevo.setInstitucionId(inst);
        nuevo.setPersonaId(personaId);
        nuevo.setOcupacion(dto.getOcupacion());
        nuevo.setEmpresa(dto.getEmpresa());
        nuevo.setEstado(EstadoAcudiente.ACTIVO);
        nuevo.setCreatedAt(ahora);
        nuevo.setUpdatedAt(ahora);
        return acudienteRepository.save(nuevo);
    }
}
