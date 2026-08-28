package backend.security.application.usecase;

import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import backend.security.infrastructure.persistence.UsuarioJpaRepository;
import backend.security.infrastructure.rest.dto.LoginResponseDto;
import backend.security.infrastructure.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de {@link LoginUseCase}: flujo de autenticación, bloqueo temporal
 * tras intentos fallidos, validación de estado y emisión del JWT. Todas las dependencias
 * (repositorios, codificador de contraseñas y servicio JWT) están simuladas con Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LoginUseCase – autenticación y bloqueo de cuenta")
class LoginUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioJpaRepository usuarioJpaRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks private LoginUseCase loginUseCase;

    private Usuario usuarioActivo(UUID id) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setUsername("jose-perez12");
        u.setPasswordHash("$2a$hash");
        u.setEstado(EstadoUsuario.ACTIVO);
        u.setTipoContexto(TipoContextoUsuario.INSTITUCION);
        u.setIntentosFallidos((short) 0);
        return u;
    }

    @Test
    @DisplayName("Login exitoso emite token, limpia intentos y devuelve roles")
    void loginExitoso() {
        UUID id = UUID.randomUUID();
        Usuario u = usuarioActivo(id);
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("secreta", "$2a$hash")).thenReturn(true);
        when(usuarioJpaRepository.findRolesByUsuarioId(id.toString())).thenReturn(List.of("DOCENTE"));
        when(jwtService.generateToken(id, "jose-perez12")).thenReturn("token-jwt");

        LoginResponseDto res = loginUseCase.login("jose-perez12", "secreta");

        assertThat(res.getToken()).isEqualTo("token-jwt");
        assertThat(res.getUsername()).isEqualTo("jose-perez12");
        assertThat(res.getRoles()).containsExactly("DOCENTE");
        assertThat(u.getIntentosFallidos()).isEqualTo((short) 0);
        assertThat(u.getBloqueadoHasta()).isNull();
        assertThat(u.getUltimoLogin()).isNotNull();
        verify(usuarioRepository).save(u);
    }

    @Test
    @DisplayName("Usuario inexistente lanza 'Credenciales inválidas' sin tocar la contraseña")
    void usuarioInexistente() {
        when(usuarioRepository.findByUsernameOrEmail("nadie")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginUseCase.login("nadie", "x"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Credenciales inválidas");

        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Cuenta bloqueada temporalmente rechaza el login aunque la clave sea correcta")
    void cuentaBloqueadaTemporalmente() {
        Usuario u = usuarioActivo(UUID.randomUUID());
        u.setBloqueadoHasta(LocalDateTime.now().plusMinutes(10));
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12")).thenReturn(Optional.of(u));

        assertThatThrownBy(() -> loginUseCase.login("jose-perez12", "secreta"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("bloqueada");

        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Cuenta no activa (INACTIVO) es rechazada")
    void cuentaNoActiva() {
        Usuario u = usuarioActivo(UUID.randomUUID());
        u.setEstado(EstadoUsuario.INACTIVO);
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12")).thenReturn(Optional.of(u));

        assertThatThrownBy(() -> loginUseCase.login("jose-perez12", "secreta"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activa");
    }

    @Test
    @DisplayName("Contraseña incorrecta incrementa intentos fallidos sin bloquear todavía")
    void passwordIncorrectaIncrementaIntentos() {
        Usuario u = usuarioActivo(UUID.randomUUID());
        u.setIntentosFallidos((short) 2);
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("mala", "$2a$hash")).thenReturn(false);

        assertThatThrownBy(() -> loginUseCase.login("jose-perez12", "mala"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Credenciales inválidas");

        assertThat(u.getIntentosFallidos()).isEqualTo((short) 3);
        assertThat(u.getBloqueadoHasta()).isNull();
        verify(usuarioRepository).save(u);
    }

    @Test
    @DisplayName("Al 5.º intento fallido la cuenta queda bloqueada ~15 minutos")
    void quintoIntentoBloqueaLaCuenta() {
        Usuario u = usuarioActivo(UUID.randomUUID());
        u.setIntentosFallidos((short) 4); // el próximo fallo es el nº 5
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("mala", "$2a$hash")).thenReturn(false);

        LocalDateTime antes = LocalDateTime.now();
        assertThatThrownBy(() -> loginUseCase.login("jose-perez12", "mala"))
                .isInstanceOf(RuntimeException.class);

        assertThat(u.getIntentosFallidos()).isEqualTo((short) 5);
        assertThat(u.getBloqueadoHasta()).isNotNull();
        assertThat(u.getBloqueadoHasta()).isAfter(antes.plusMinutes(14));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertThat(captor.getValue().getBloqueadoHasta()).isNotNull();
    }
}
