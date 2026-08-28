package backend.security.infrastructure.rest;

import backend.security.domain.model.EstadoUsuario;
import backend.support.AbstractIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integración de las REGLAS DE SEGURIDAD del login contra la BD real: el bloqueo tras
 * varios intentos fallidos (con el contador persistido) y el rechazo de cuentas inactivas.
 * Con rollback (@Transactional).
 */
@Transactional
@DisplayName("Integración · Reglas de seguridad del login (bloqueo, estado)")
class LoginSeguridadIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("PI-10 · Tras 5 intentos fallidos la cuenta queda bloqueada")
    void bloqueoTrasCincoIntentos() throws Exception {
        final String username = "it-lock-" + UUID.randomUUID().toString().substring(0, 8);
        sembrarUsuario(username, "Correcta1234", EstadoUsuario.ACTIVO);

        String malo = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"claveMala\"}";
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(malo))
                   .andExpect(status().isUnauthorized());
        }

        // 6.º intento: aunque la contraseña sea la correcta, la cuenta ya está bloqueada.
        String bueno = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"Correcta1234\"}";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bueno))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.mensaje", Matchers.containsString("bloquea")));
    }

    @Test
    @DisplayName("PI-11 · Un usuario INACTIVO no puede iniciar sesión")
    void usuarioInactivoRechazado() throws Exception {
        final String username = "it-inact-" + UUID.randomUUID().toString().substring(0, 8);
        sembrarUsuario(username, "Correcta1234", EstadoUsuario.INACTIVO);

        String body = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"Correcta1234\"}";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
               .andExpect(status().isUnauthorized())
               .andExpect(jsonPath("$.mensaje", Matchers.containsString("activa")));
    }
}
