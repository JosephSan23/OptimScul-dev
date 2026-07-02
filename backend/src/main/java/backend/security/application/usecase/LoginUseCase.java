package backend.security.application.usecase;

import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.Usuario;
import backend.security.infrastructure.persistence.UsuarioJpaRepository;
import backend.security.infrastructure.rest.dto.LoginResponseDto;
import backend.security.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginUseCase {

    private static final int MAX_INTENTOS_FALLIDOS = 5;
    private static final int MINUTOS_BLOQUEO = 15;

    private final UsuarioRepository usuarioRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUseCase(UsuarioRepository usuarioRepository,
                        UsuarioJpaRepository usuarioJpaRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService) {
        this.usuarioRepository    = usuarioRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.passwordEncoder      = passwordEncoder;
        this.jwtService           = jwtService;
    }

    @Transactional
    public LoginResponseDto login(String usernameOrEmail, String rawPassword) {

        Usuario usuario = usuarioRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // ── Verificar bloqueo temporal ──
        if (usuario.getBloqueadoHasta() != null &&
            usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Cuenta bloqueada temporalmente. Intenta en " +
                MINUTOS_BLOQUEO + " minutos.");
        }

        // ── Verificar estado ──
        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("La cuenta no está activa.");
        }

        // ── Verificar contraseña ──
        if (!passwordEncoder.matches(rawPassword, usuario.getPasswordHash())) {
            registrarIntentoFallido(usuario);
            throw new RuntimeException("Credenciales inválidas");
        }

        // ── Login exitoso ──
        usuario.setIntentosFallidos((short) 0);
        usuario.setBloqueadoHasta(null);
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // ── Obtener roles desde usuario_rol ──
        List<String> roles = obtenerRoles(usuario.getId().toString());

        String token = jwtService.generateToken(usuario.getId(), usuario.getUsername());

        return new LoginResponseDto(
                token,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getTipoContexto() != null ? usuario.getTipoContexto().name() : null,
                roles
        );
    }

    private List<String> obtenerRoles(String usuarioId) {
        // Consulta nativa para traer los códigos de rol del usuario
        return usuarioJpaRepository.findRolesByUsuarioId(usuarioId);
    }

    private void registrarIntentoFallido(Usuario usuario) {
        int intentos = (usuario.getIntentosFallidos() == null
                ? 0 : usuario.getIntentosFallidos()) + 1;
        usuario.setIntentosFallidos((short) intentos);

        if (intentos >= MAX_INTENTOS_FALLIDOS) {
            usuario.setBloqueadoHasta(LocalDateTime.now().plusMinutes(MINUTOS_BLOQUEO));
        }
        usuarioRepository.save(usuario);
    }
}