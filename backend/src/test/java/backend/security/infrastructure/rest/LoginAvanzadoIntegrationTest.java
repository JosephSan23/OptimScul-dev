package backend.security.infrastructure.rest;

import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.Usuario;
import backend.support.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integración de comportamientos avanzados del login contra la BD real: iniciar sesión con
 * el CORREO (no solo el usuario) y el RESETEO del contador de intentos tras un login
 * exitoso. Con rollback (@Transactional).
 */
@Transactional
@DisplayName("Integración · Login por correo y reseteo de intentos")
class LoginAvanzadoIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("PI-12 · Se puede iniciar sesión con el correo, no solo con el usuario")
    void loginConCorreo() throws Exception {
        final String clave = "Prueba1234";
        final String username = "it-mail-" + UUID.randomUUID().toString().substring(0, 8);
        final String correo = "it-" + UUID.randomUUID().toString().substring(0, 8) + "@demo.com";

        Usuario u = sembrarUsuario(username, clave, EstadoUsuario.ACTIVO);
        u.setEmailLogin(correo);
        usuarioRepository.save(u);
        em.flush();

        // Inicia sesión usando el CORREO en el campo usernameOrEmail.
        String body = "{\"usernameOrEmail\":\"" + correo + "\",\"password\":\"" + clave + "\"}";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").isNotEmpty())
               .andExpect(jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("PI-15 · Un login exitoso reinicia el contador de intentos y registra el último acceso")
    void loginExitosoReiniciaIntentos() throws Exception {
        final String clave = "Correcta1234";
        final String username = "it-reset-" + UUID.randomUUID().toString().substring(0, 8);
        sembrarUsuario(username, clave, EstadoUsuario.ACTIVO);

        // Dos intentos fallidos → el contador sube.
        String malo = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"claveMala\"}";
        for (int i = 0; i < 2; i++) {
            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(malo))
                   .andExpect(status().isUnauthorized());
        }

        // Login correcto → debe reiniciar intentos y fijar último acceso.
        String bueno = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"" + clave + "\"}";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bueno))
               .andExpect(status().isOk());

        Usuario tras = usuarioRepository.findByUsernameOrEmail(username).orElseThrow();
        assertThat(tras.getIntentosFallidos()).isEqualTo((short) 0);
        assertThat(tras.getBloqueadoHasta()).isNull();
        assertThat(tras.getUltimoLogin()).isNotNull();
    }
}
