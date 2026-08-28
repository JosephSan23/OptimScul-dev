package backend.security.infrastructure.rest;

import backend.security.domain.model.EstadoUsuario;
import backend.support.AbstractIntegrationTest;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integración END-TO-END del token: inicia sesión, toma el JWT de la respuesta y con él
 * accede a un endpoint protegido (/api/perfil). Verifica el ciclo completo de seguridad:
 * emisión del token en el login y su aceptación por el JwtAuthFilter en la siguiente
 * petición. Con rollback (@Transactional).
 */
@Transactional
@DisplayName("Integración · Acceso a endpoint protegido con el token del login")
class PerfilAccesoIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("PI-09 · Con el token del login se accede al perfil (200)")
    void loginYAccesoAPerfilConToken() throws Exception {
        final String clave = "Prueba1234";
        final String username = "it-perfil-" + UUID.randomUUID().toString().substring(0, 8);
        sembrarUsuario(username, clave, EstadoUsuario.ACTIVO);

        // 1) Login → obtener el token del cuerpo de la respuesta
        String loginBody = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"" + clave + "\"}";
        String respuesta = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String token = JsonPath.read(respuesta, "$.token");

        // 2) Usar el token para acceder al endpoint protegido
        mockMvc.perform(get("/api/perfil")
                        .header("Authorization", "Bearer " + token))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.username").value(username));
    }
}
