package backend.community.application.usecase.AcudienteCrud;

import backend.community.infrastructure.rest.dto.AcudienteRequestDto;
import backend.onboarding.application.AltaUsuarioInstitucionService;
import backend.people.application.port.*;
import backend.people.domain.model.*;
import backend.security.application.AutorizacionService;
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
    private final AutorizacionService auth;

    public CrearVincularAcudienteUseCase(AltaUsuarioInstitucionService altaUsuario,
            EstudianteRepository estudianteRepository,
            PersonaRepository personaRepository, AcudienteRepository acudienteRepository,
            EstudianteAcudienteRepository vinculoRepository, AutorizacionService auth) {
        this.altaUsuario = altaUsuario;
        this.estudianteRepository = estudianteRepository;
        this.personaRepository = personaRepository;
        this.acudienteRepository = acudienteRepository;
        this.vinculoRepository = vinculoRepository;
        this.auth = auth;
    }

    public record Resultado(boolean reutilizado, String username) {
    }

    @Transactional
    public Resultado ejecutar(UUID coordId, UUID estudianteId, AcudienteRequestDto dto) {
        String numeroDocumento = dto.getNumeroDocumento() == null
                ? null : dto.getNumeroDocumento().trim();                            // ← NUEVA
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        Estudiante est = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(est.getInstitucionId()))
            throw new SecurityException("El estudiante es de otra institución.");

        LocalDateTime ahora = LocalDateTime.now();

        Optional<Persona> personaExistente = personaRepository.findByNumeroDocumento(numeroDocumento);
        Acudiente acudiente;
        String username = null;

        if (personaExistente.isEmpty()) {
            // ── Caso 1: persona totalmente nueva → cuenta ACUDIENTE + registro acudiente ──
            AltaUsuarioInstitucionService.Resultado cuenta = altaUsuario.provisionar(
                    inst, "ACUDIENTE", dto.getTipoDocumento(), numeroDocumento,
                    dto.getPrimerNombre(), dto.getPrimerApellido(), dto.getCorreo());
            username = cuenta.usuario().getUsername();
            acudiente = crearRegistroAcudiente(inst, cuenta.persona().getId(), dto, ahora);

        } else {
            Persona persona = personaExistente.get();
            Acudiente existente = acudienteRepository.findByPersonaId(persona.getId()).orElse(null);

            if (existente != null) {
                // ── Caso 2 (hermanos): ya es acudiente → solo se vinculará al otro estudiante ──
                if (!inst.equals(existente.getInstitucionId()))
                    throw new RuntimeException("Ese acudiente pertenece a otra institución.");
                acudiente = existente;

            } else {
                // ── Caso 3 (doble rol): la persona existe (docente, staff...) pero aún no es acudiente.
                //    provisionar reutiliza su persona y su cuenta, y solo le agrega el rol ACUDIENTE. ──
                altaUsuario.provisionar(
                        inst, "ACUDIENTE", dto.getTipoDocumento(), numeroDocumento,
                        dto.getPrimerNombre(), dto.getPrimerApellido(), dto.getCorreo());
                acudiente = crearRegistroAcudiente(inst, persona.getId(), dto, ahora);
                // username queda null: ya tenía cuenta, entra con sus credenciales de siempre
            }
        }

        // Evitar vínculo duplicado
        if (vinculoRepository.existsByEstudianteIdAndAcudienteId(estudianteId, acudiente.getId())) {
            throw new RuntimeException("Ese acudiente ya está vinculado a este estudiante.");
        }

        // Crear el vínculo
        EstudianteAcudiente v = new EstudianteAcudiente();
        v.setId(UUID.randomUUID());
        v.setEstudianteId(estudianteId);
        v.setAcudienteId(acudiente.getId());
        v.setParentesco(dto.getParentesco());
        v.setEsPrincipal(dto.getEsPrincipal() != null && dto.getEsPrincipal());
        v.setAutorizadoRecogida(dto.getAutorizadoRecogida() != null && dto.getAutorizadoRecogida());
        v.setAutorizadoInfoAcademica(dto.getAutorizadoInfoAcademica() != null && dto.getAutorizadoInfoAcademica());
        v.setCreatedAt(ahora);
        v.setUpdatedAt(ahora);
        vinculoRepository.save(v);

        return new Resultado(username == null, username);
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