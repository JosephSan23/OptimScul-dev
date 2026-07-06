package backend.security.application;

import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.application.port.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CorreoInstitucionalService {

    private final UsuarioInstitucionRepository usuarioInstitucionRepository;
    private final UsuarioRepository usuarioRepository;

    public CorreoInstitucionalService(UsuarioInstitucionRepository usuarioInstitucionRepository,
                                      UsuarioRepository usuarioRepository) {
        this.usuarioInstitucionRepository = usuarioInstitucionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** Regenera email_login = username@nuevoDominio para todos los usuarios de la institución. */
    public void regenerarDominio(UUID institucionId, String nuevoDominio) {
        LocalDateTime ahora = LocalDateTime.now();
        usuarioInstitucionRepository.findByInstitucionId(institucionId).forEach(vinculo ->
            usuarioRepository.findById(vinculo.getUsuarioId()).ifPresent(usuario -> {
                usuario.setEmailLogin(usuario.getUsername() + "@" + nuevoDominio);
                usuario.setUpdatedAt(ahora);
                usuarioRepository.save(usuario);
            })
        );
    }
}