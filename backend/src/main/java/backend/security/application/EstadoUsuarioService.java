package backend.security.application;

import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EstadoUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public EstadoUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void activar(UUID usuarioId)   { cambiar(usuarioId, EstadoUsuario.ACTIVO); }

    @Transactional
    public void inactivar(UUID usuarioId) { cambiar(usuarioId, EstadoUsuario.INACTIVO); }

    private void cambiar(UUID usuarioId, EstadoUsuario nuevo) {
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe."));
        u.setEstado(nuevo);
        u.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(u);
    }
}