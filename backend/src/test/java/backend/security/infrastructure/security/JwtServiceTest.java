package backend.security.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Pruebas unitarias de {@link JwtService}: generación, lectura y validación de tokens JWT.
 * No requieren contexto de Spring; el secreto y la expiración se inyectan por reflexión
 * (equivalen a las propiedades jwt.secret / jwt.expiration-ms).
 */
@DisplayName("JwtService – emisión y validación de tokens")
class JwtServiceTest {

    // Debe medir al menos 32 bytes para HMAC-SHA256 (requisito de jjwt).
    private static final String SECRET = "clave-super-secreta-de-pruebas-optimscul-1234567890";
    private static final long EXPIRACION_MS = 3_600_000L; // 1 hora

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "expirationMs", EXPIRACION_MS);
    }

    @Test
    @DisplayName("El token generado conserva el username (subject) y el usuarioId como claim")
    void generaTokenConDatosDelUsuario() {
        UUID usuarioId = UUID.randomUUID();

        String token = jwtService.generateToken(usuarioId, "jose-perez12");

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractUsername(token)).isEqualTo("jose-perez12");
        assertThat(jwtService.extractUsuarioId(token)).isEqualTo(usuarioId);
    }

    @Test
    @DisplayName("isTokenValid es verdadero solo cuando el username coincide con el del token")
    void validaTokenSegunUsername() {
        String token = jwtService.generateToken(UUID.randomUUID(), "docente1");

        assertThat(jwtService.isTokenValid(token, "docente1")).isTrue();
        assertThat(jwtService.isTokenValid(token, "otro-usuario")).isFalse();
    }

    @Test
    @DisplayName("Un token expirado lanza ExpiredJwtException al leerse")
    void rechazaTokenExpirado() {
        // Expiración negativa => el token nace vencido.
        ReflectionTestUtils.setField(jwtService, "expirationMs", -1_000L);
        String tokenVencido = jwtService.generateToken(UUID.randomUUID(), "docente1");

        assertThatThrownBy(() -> jwtService.extractUsername(tokenVencido))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    @DisplayName("Un token firmado con otra clave es rechazado por firma inválida")
    void rechazaTokenConFirmaInvalida() {
        // Segundo servicio con un secreto distinto: firma que el primero no reconoce.
        JwtService otroEmisor = new JwtService();
        ReflectionTestUtils.setField(otroEmisor, "secret", "otra-clave-distinta-de-32-bytes-o-mas-abcdefghij");
        ReflectionTestUtils.setField(otroEmisor, "expirationMs", EXPIRACION_MS);
        String tokenAjeno = otroEmisor.generateToken(UUID.randomUUID(), "intruso");

        assertThatThrownBy(() -> jwtService.extractUsername(tokenAjeno))
                .isInstanceOf(JwtException.class);
    }

    @Test
    @DisplayName("Un token manipulado o malformado es rechazado")
    void rechazaTokenManipulado() {
        String token = jwtService.generateToken(UUID.randomUUID(), "docente1");
        String tokenAlterado = token.substring(0, token.length() - 3) + "abc";

        assertThatThrownBy(() -> jwtService.extractUsername(tokenAlterado))
                .isInstanceOf(JwtException.class);
    }
}
