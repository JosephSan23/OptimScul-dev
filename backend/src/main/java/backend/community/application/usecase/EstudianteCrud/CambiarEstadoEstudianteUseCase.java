package backend.community.application.usecase.EstudianteCrud;

import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.EstadoEstudiante;
import backend.people.domain.model.Estudiante;
import backend.security.application.AutorizacionService;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CambiarEstadoEstudianteUseCase {
    private final EstudianteRepository repo;
    private final UsuarioRepository usuarioRepository;
    private final AutorizacionService auth;

    public CambiarEstadoEstudianteUseCase(EstudianteRepository repo, UsuarioRepository usuarioRepository,
            AutorizacionService auth) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
        this.auth = auth;
    }

    @Transactional
    public void activar(UUID c, UUID id) {
        cambiar(c, id, EstadoEstudiante.ACTIVO);
    }

    @Transactional
    public void inactivar(UUID c, UUID id) {
        cambiar(c, id, EstadoEstudiante.INACTIVO);
    }

    private void cambiar(UUID coordId, UUID id, EstadoEstudiante estado) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        Estudiante e = repo.findById(id).orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(e.getInstitucionId()))
            throw new SecurityException("No puedes cambiar estudiantes de otra institución.");
        LocalDateTime ahora = LocalDateTime.now();
        e.setEstado(estado);
        e.setUpdatedAt(ahora);
        repo.save(e);
        usuarioRepository.findByPersonaId(e.getPersonaId()).ifPresent(u -> {
            u.setEstado(estado == EstadoEstudiante.ACTIVO ? EstadoUsuario.ACTIVO : EstadoUsuario.INACTIVO);
            u.setUpdatedAt(ahora);
            usuarioRepository.save(u);
        });
    }
}