package backend.community.application.usecase.EstudianteCrud;

import backend.community.infrastructure.rest.dto.EditarEstudianteRequestDto;
import backend.people.application.port.EstudianteRepository;
import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.EstadoEstudiante;
import backend.people.domain.model.Estudiante;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.AutorizacionService;
import backend.security.application.UsernameGenerator;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditarEstudianteUseCase {

    private final EstudianteRepository estudianteRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstitucionRepository institucionRepository;
    private final UsernameGenerator usernameGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AutorizacionService auth;

    public EditarEstudianteUseCase(EstudianteRepository estudianteRepository, PersonaRepository personaRepository,
                                   UsuarioRepository usuarioRepository, InstitucionRepository institucionRepository,
                                   UsernameGenerator usernameGenerator, PasswordEncoder passwordEncoder,
                                   AutorizacionService auth) {
        this.estudianteRepository = estudianteRepository;
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.institucionRepository = institucionRepository;
        this.usernameGenerator = usernameGenerator;
        this.passwordEncoder = passwordEncoder;
        this.auth = auth;
    }

    @Transactional
    public void ejecutar(UUID coordId, UUID estudianteId, EditarEstudianteRequestDto dto) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        Estudiante e = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(e.getInstitucionId())) throw new SecurityException("No puedes editar estudiantes de otra institución.");

        Persona p = personaRepository.findById(e.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la persona."));

        boolean documentoCambio = !p.getNumeroDocumento().equals(dto.getNumeroDocumento());
        if (documentoCambio && personaRepository.existsByNumeroDocumento(dto.getNumeroDocumento())) {
            throw new RuntimeException("Ya existe otra persona con ese documento.");
        }

        LocalDateTime ahora = LocalDateTime.now();

        // Persona
        p.setTipoDocumento(TipoDocumentoPersona.valueOf(dto.getTipoDocumento()));
        p.setNumeroDocumento(dto.getNumeroDocumento());
        p.setPrimerNombre(dto.getPrimerNombre());
        p.setSegundoNombre(dto.getSegundoNombre());
        p.setPrimerApellido(dto.getPrimerApellido());
        p.setSegundoApellido(dto.getSegundoApellido());
        p.setCorreo(dto.getCorreo());
        p.setTelefono(dto.getTelefono());
        p.setFechaNacimiento(dto.getFechaNacimiento());
        p.setDireccion(dto.getDireccion());
        p.setCiudad(dto.getCiudad());
        p.setUpdatedAt(ahora);
        personaRepository.save(p);

        // Estudiante
        e.setFechaIngreso(dto.getFechaIngreso());
        if (dto.getEstado() != null) e.setEstado(dto.getEstado());
        e.setObservaciones(dto.getObservaciones());
        e.setUpdatedAt(ahora);
        estudianteRepository.save(e);

        // Cuenta del estudiante
        usuarioRepository.findByPersonaId(p.getId()).ifPresent(usuario -> {
            // A. Credenciales: solo si NUNCA ha iniciado sesión (corregir errores de creación)
            if (usuario.getUltimoLogin() == null) {
                Institucion institucion = institucionRepository.findById(inst)
                        .orElseThrow(() -> new RuntimeException("La institución no existe."));
                String username = usernameGenerator.generar(dto.getPrimerNombre(), dto.getPrimerApellido(),
                        dto.getNumeroDocumento(), usuario.getId());
                usuario.setUsername(username);
                usuario.setEmailLogin(username + "@" + institucion.getDominioCorreo());
                if (documentoCambio) {
                    usuario.setPasswordHash(passwordEncoder.encode(dto.getNumeroDocumento()));
                    usuario.setRequiereCambioPassword(true);
                }
            }
            // B. Acceso ligado al estado académico: solo ACTIVO puede entrar
            usuario.setEstado(e.getEstado() == EstadoEstudiante.ACTIVO ? EstadoUsuario.ACTIVO : EstadoUsuario.INACTIVO);
            usuario.setUpdatedAt(ahora);
            usuarioRepository.save(usuario);
        });
    }
}