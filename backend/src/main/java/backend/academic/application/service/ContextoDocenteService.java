package backend.academic.application.service;

import backend.people.application.port.ProfesorRepository;
import backend.people.domain.model.Profesor;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ContextoDocenteService {

    private final UsuarioRepository usuarioRepository;
    private final ProfesorRepository profesorRepository;
    private final AutorizacionService auth;

    public ContextoDocenteService(UsuarioRepository usuarioRepository, ProfesorRepository profesorRepository,
                                  AutorizacionService auth) {
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
        this.auth = auth;
    }

    public record Contexto(UUID institucionId, UUID profesorId) {}

    /** Resuelve el profesor del usuario logueado, exigiendo rol DOCENTE. */
    public Contexto resolver(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, "DOCENTE");
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Profesor prof = profesorRepository.findByInstitucionIdAndPersonaId(inst, u.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No tienes un perfil de profesor en esta institución."));
        return new Contexto(inst, prof.getId());
    }
}