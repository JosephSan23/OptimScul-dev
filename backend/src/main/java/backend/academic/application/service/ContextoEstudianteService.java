package backend.academic.application.service;

import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.Estudiante;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ContextoEstudianteService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;
    private final AutorizacionService auth;

    public ContextoEstudianteService(UsuarioRepository usuarioRepository, EstudianteRepository estudianteRepository,
            AutorizacionService auth) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
        this.auth = auth;
    }

    public record Contexto(UUID institucionId, UUID estudianteId, UUID personaId) {
    }

    public Contexto resolver(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, "ESTUDIANTE");
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Estudiante e = estudianteRepository.findByInstitucionIdAndPersonaId(inst, u.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No tienes un perfil de estudiante."));
        return new Contexto(inst, e.getId(), u.getPersonaId());
    }
}