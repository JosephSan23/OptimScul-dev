package backend.security.application;

import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UsernameGeneratorTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsernameGenerator usernameGenerator;

    @Test
    @DisplayName("Genera username en minúsculas, sin tildes y con 2 dígitos del documento")
    void generaUsernameBase() {
        when(usuarioRepository.findByUsernameOrEmail(anyString()))
                .thenReturn(Optional.empty());

        String username = usernameGenerator.generar("José", "Pérez", "12345678");

        assertThat(username).isEqualTo("jose-perez12");
    }

    @Test
    @DisplayName("Si el username ya está ocupado, agrega el sufijo -1")
    void resuelveDuplicado() {
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12"))
                .thenReturn(Optional.of(mock(Usuario.class)));
        when(usuarioRepository.findByUsernameOrEmail("jose-perez12-1"))
                .thenReturn(Optional.empty());

        String username = usernameGenerator.generar("José", "Pérez", "12345678");

        assertThat(username).isEqualTo("jose-perez12-1");
    }
}