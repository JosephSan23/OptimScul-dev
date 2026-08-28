package backend.security.infrastructure.rest;

import backend.support.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Pruebas de INTEGRACIÓN de autenticación y seguridad (casos que no requieren sembrar
 * datos). Recorren el filtro de seguridad real, el AuthController y, cuando aplica, el
 * LoginUseCase contra PostgreSQL.
 */
@DisplayName("Integración · Autenticación y seguridad (PostgreSQL real)")
class AuthIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("PI-02 · Una ruta protegida sin token JWT es rechazada")
    void rutaProtegidaSinTokenEsRechazada() throws Exception {
        mockMvc.perform(get("/api/perfil"))
               .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("PI-03 · Login con campos vacíos devuelve 400 (Bean Validation)")
    void loginCamposVaciosDevuelve400() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PI-04 · Login con credenciales inexistentes devuelve 401")
    void loginCredencialesInvalidasDevuelve401() throws Exception {
        String body = "{\"usernameOrEmail\":\"noexiste@demo.com\",\"password\":\"ClaveMala123\"}";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PI-05 · El endpoint público de salud responde sin token")
    void healthEsPublico() throws Exception {
        mockMvc.perform(get("/actuator/health"))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PI-06 · Una ruta protegida con token JWT inválido es rechazada")
    void rutaProtegidaConTokenInvalido() throws Exception {
        mockMvc.perform(get("/api/perfil")
                        .header("Authorization", "Bearer token.corrupto.invalido"))
               .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("PI-14 · Un header Authorization sin prefijo 'Bearer' es ignorado y la ruta se rechaza")
    void headerSinBearerEsRechazado() throws Exception {
        // El JwtAuthFilter solo procesa headers que empiezan por 'Bearer '; cualquier otro
        // esquema se ignora y la petición queda sin autenticar.
        mockMvc.perform(get("/api/perfil")
                        .header("Authorization", "Basic dXNlcjpwYXNz"))
               .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("PI-16 · Un cuerpo JSON malformado devuelve 400")
    void jsonMalformadoDevuelve400() throws Exception {
        // Lo maneja el GlobalExceptionHandler (HttpMessageNotReadableException → 400).
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ esto no es json valido "))
               .andExpect(status().isBadRequest());
    }
}
