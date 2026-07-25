package backend.academic.application.service;

import backend.people.application.port.AcudienteRepository;
import backend.people.domain.model.Acudiente;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ContextoAcudienteService {
    private final UsuarioRepository usuarioRepository;
    private final AcudienteRepository acudienteRepository;
    private final AutorizacionService auth;

    public ContextoAcudienteService(UsuarioRepository usuarioRepository, AcudienteRepository acudienteRepository,
                                    AutorizacionService auth) {
        this.usuarioRepository = usuarioRepository; this.acudienteRepository = acudienteRepository; this.auth = auth;
    }

    public record Contexto(UUID institucionId, UUID acudienteId) {}

    public Contexto resolver(UUID usuarioId) {
        UUID inst = auth.institucionConRol(usuarioId, "ACUDIENTE");
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Acudiente a = acudienteRepository.findByPersonaId(u.getPersonaId())
                .orElseThrow(() -> new RuntimeException("No tienes un perfil de acudiente."));
        if (!inst.equals(a.getInstitucionId()))
            throw new SecurityException("El acudiente no pertenece a tu institución.");
        return new Contexto(inst, a.getId());
    }
}