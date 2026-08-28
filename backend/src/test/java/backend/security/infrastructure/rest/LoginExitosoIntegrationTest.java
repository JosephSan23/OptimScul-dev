package backend.security.infrastructure.rest;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import backend.support.AbstractIntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integración del CAMINO FELIZ de login: siembra una persona + usuario reales (con
 * contraseña cifrada por BCrypt) en la BD, y verifica que POST /api/auth/login devuelve
 * 200 y un token JWT. Es el flujo completo del módulo: HTTP → seguridad → LoginUseCase →
 * repositorio → PostgreSQL → generación del token.
 *
 * Toda la prueba corre en una transacción que hace ROLLBACK al final (@Transactional),
 * por lo que no deja residuos en la base.
 */
@Transactional
@DisplayName("Integración · Login exitoso (siembra usuario, con rollback)")
class LoginExitosoIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("PI-07 · Login con credenciales válidas devuelve 200 y un token JWT")
    void loginExitosoDevuelveToken() throws Exception {
        final String clave = "Prueba1234";
        final String username = "it-login-" + UUID.randomUUID().toString().substring(0, 8);
        final LocalDateTime ahora = LocalDateTime.now();

        // ── Sembrar la persona (columnas NOT NULL de la tabla persona) ──
        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setTipoDocumento(TipoDocumentoPersona.CC);
        persona.setNumeroDocumento("IT" + System.nanoTime());
        persona.setPrimerNombre("Prueba");
        persona.setPrimerApellido("Integracion");
        persona.setPais("Colombia");
        persona.setCreatedAt(ahora);
        persona.setUpdatedAt(ahora);
        personaRepository.save(persona);

        // ── Sembrar el usuario ACTIVO con contraseña BCrypt ──
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setPersonaId(persona.getId());
        usuario.setUsername(username);
        usuario.setPasswordHash(passwordEncoder.encode(clave));
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setTipoContexto(TipoContextoUsuario.INSTITUCION);
        usuario.setRequiereCambioPassword(false);
        usuario.setEmailVerificado(true);
        usuario.setDobleFactorHabilitado(false);
        usuario.setIntentosFallidos((short) 0);
        usuario.setCreatedAt(ahora);
        usuario.setUpdatedAt(ahora);
        usuarioRepository.save(usuario);

        em.flush(); // fuerza los INSERT para que el login los vea dentro de la misma transacción

        // ── Ejecutar el login por HTTP ──
        String body = "{\"usernameOrEmail\":\"" + username + "\",\"password\":\"" + clave + "\"}";
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").isNotEmpty())
               .andExpect(jsonPath("$.username").value(username));
    }
}
