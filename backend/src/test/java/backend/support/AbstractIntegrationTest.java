package backend.support;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.EstadoUsuario;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base para las pruebas de INTEGRACIÓN.
 *
 * Se conecta al PostgreSQL que ya está corriendo (contenedor optimscul_db, puerto 5433,
 * con el esquema real) tomando la configuración del perfil "dev". El perfil "test" apaga
 * Flyway y deja Hibernate en ddl-auto: none para NO tocar el esquema.
 *
 * Ofrece un MockMvc con la cadena de seguridad aplicada y un helper para SEMBRAR usuarios.
 * Las pruebas que escriben datos deben anotarse con @Transactional para hacer rollback.
 */
@SpringBootTest
@ActiveProfiles({"dev", "test"})
public abstract class AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected UsuarioRepository usuarioRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @PersistenceContext
    protected EntityManager em;

    protected MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    /**
     * Siembra una persona + un usuario con la contraseña dada (cifrada con BCrypt) y el
     * estado indicado. Documento y username se hacen únicos. Devuelve el usuario creado.
     * Debe llamarse dentro de una prueba @Transactional (rollback).
     */
    protected Usuario sembrarUsuario(String username, String claveEnClaro, EstadoUsuario estado) {
        LocalDateTime ahora = LocalDateTime.now();

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

        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setPersonaId(persona.getId());
        usuario.setUsername(username);
        usuario.setPasswordHash(passwordEncoder.encode(claveEnClaro));
        usuario.setEstado(estado);
        usuario.setTipoContexto(TipoContextoUsuario.INSTITUCION);
        usuario.setRequiereCambioPassword(false);
        usuario.setEmailVerificado(true);
        usuario.setDobleFactorHabilitado(false);
        usuario.setIntentosFallidos((short) 0);
        usuario.setCreatedAt(ahora);
        usuario.setUpdatedAt(ahora);
        usuarioRepository.save(usuario);

        em.flush(); // asegura que el usuario sea visible al login dentro de la misma transacción
        return usuario;
    }
}
