package backend.people.infrastructure.persistence;

import backend.people.application.port.PersonaRepository;
import backend.people.domain.model.Persona;
import backend.people.domain.model.Sexo;
import backend.people.domain.model.TipoDocumentoPersona;
import backend.support.AbstractIntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integración de PERSISTENCIA: guarda personas con el repositorio real y las vuelve a leer
 * contra PostgreSQL, verificando el mapeo JPA — incluidos los tipos enum propios de
 * PostgreSQL. Con rollback (@Transactional).
 */
@Transactional
@DisplayName("Integración · Persistencia de Persona (guardar y recuperar)")
class PersonaPersistenceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PersonaRepository personaRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("PI-08 · Guarda una persona y la recupera por número de documento")
    void guardaYRecuperaPersona() {
        final String documento = "IT" + System.nanoTime();
        final LocalDateTime ahora = LocalDateTime.now();

        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setTipoDocumento(TipoDocumentoPersona.CC);
        persona.setNumeroDocumento(documento);
        persona.setPrimerNombre("Ana");
        persona.setPrimerApellido("Gomez");
        persona.setCorreo("ana.it@correo.com");
        persona.setPais("Colombia");
        persona.setCreatedAt(ahora);
        persona.setUpdatedAt(ahora);

        personaRepo.save(persona);
        entityManager.flush();

        Optional<Persona> encontrada = personaRepo.findByNumeroDocumento(documento);

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getPrimerNombre()).isEqualTo("Ana");
        assertThat(encontrada.get().getPrimerApellido()).isEqualTo("Gomez");
        assertThat(encontrada.get().getTipoDocumento()).isEqualTo(TipoDocumentoPersona.CC);
        assertThat(encontrada.get().getId()).isEqualTo(persona.getId());
    }

    @Test
    @DisplayName("PI-13 · Los tipos enum de PostgreSQL (tipoDocumento, sexo) se mapean correctamente")
    void mapeaEnumsDePostgres() {
        final String documento = "IT" + System.nanoTime();
        final LocalDateTime ahora = LocalDateTime.now();

        Persona persona = new Persona();
        persona.setId(UUID.randomUUID());
        persona.setTipoDocumento(TipoDocumentoPersona.CE); // enum tipo_documento_persona_enum
        persona.setNumeroDocumento(documento);
        persona.setPrimerNombre("Luis");
        persona.setPrimerApellido("Perez");
        persona.setSexo(Sexo.MASCULINO);                   // enum sexo_enum
        persona.setPais("Colombia");
        persona.setCreatedAt(ahora);
        persona.setUpdatedAt(ahora);

        personaRepo.save(persona);
        entityManager.flush();
        entityManager.clear(); // fuerza recargar desde la BD, no desde la caché de sesión

        Optional<Persona> recargada = personaRepo.findByNumeroDocumento(documento);

        assertThat(recargada).isPresent();
        assertThat(recargada.get().getTipoDocumento()).isEqualTo(TipoDocumentoPersona.CE);
        assertThat(recargada.get().getSexo()).isEqualTo(Sexo.MASCULINO);
    }
}
