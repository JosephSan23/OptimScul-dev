package backend.community.application.usecase.AcudienteCrud;

import backend.community.infrastructure.rest.dto.EditarAcudienteRequestDto;
import backend.people.application.port.AcudienteRepository;
import backend.people.application.port.EstudianteAcudienteRepository;
import backend.people.application.port.InstitucionRepository;
import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.*;
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
public class EditarAcudienteUseCase {

    private final EstudianteAcudienteRepository vinculoRepository;
    private final AcudienteRepository acudienteRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstitucionRepository institucionRepository;
    private final UsernameGenerator usernameGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AutorizacionService auth;

    public EditarAcudienteUseCase(EstudianteAcudienteRepository vinculoRepository,
            AcudienteRepository acudienteRepository,
            PersonaRepository personaRepository, UsuarioRepository usuarioRepository,
            InstitucionRepository institucionRepository, UsernameGenerator usernameGenerator,
            PasswordEncoder passwordEncoder, AutorizacionService auth) {
        this.vinculoRepository = vinculoRepository;
        this.acudienteRepository = acudienteRepository;
        this.personaRepository = personaRepository;
        this.usuarioRepository = usuarioRepository;
        this.institucionRepository = institucionRepository;
        this.usernameGenerator = usernameGenerator;
        this.passwordEncoder = passwordEncoder;
        this.auth = auth;
    }

    @Transactional
    public void ejecutar(UUID coordId, UUID vinculoId, EditarAcudienteRequestDto dto) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        EstudianteAcudiente v = vinculoRepository.findById(vinculoId)
                .orElseThrow(() -> new RuntimeException("El vínculo no existe."));
        Acudiente a = acudienteRepository.findById(v.getAcudienteId())
                .orElseThrow(() -> new RuntimeException("El acudiente no existe."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("Acudiente de otra institución.");
        Persona p = personaRepository.findById(a.getPersonaId())
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
        p.setUpdatedAt(ahora);
        personaRepository.save(p);

        // Acudiente
        a.setOcupacion(dto.getOcupacion());
        a.setEmpresa(dto.getEmpresa());
        if (dto.getEstado() != null)
            a.setEstado(dto.getEstado());
        a.setUpdatedAt(ahora);
        acudienteRepository.save(a);

        // Vínculo (parentesco/permisos de ESTE estudiante)
        v.setParentesco(dto.getParentesco());
        v.setEsPrincipal(dto.getEsPrincipal() != null && dto.getEsPrincipal());
        v.setAutorizadoRecogida(dto.getAutorizadoRecogida() != null && dto.getAutorizadoRecogida());
        v.setAutorizadoInfoAcademica(dto.getAutorizadoInfoAcademica() != null && dto.getAutorizadoInfoAcademica());
        v.setUpdatedAt(ahora);
        vinculoRepository.save(v);

        // Cuenta (regen si no ha entrado + acceso ligado al estado)
        usuarioRepository.findByPersonaId(p.getId()).ifPresent(usuario -> {
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
            usuario.setEstado(a.getEstado() == EstadoAcudiente.ACTIVO ? EstadoUsuario.ACTIVO : EstadoUsuario.INACTIVO);
            usuario.setUpdatedAt(ahora);
            usuarioRepository.save(usuario);
        });
    }
}