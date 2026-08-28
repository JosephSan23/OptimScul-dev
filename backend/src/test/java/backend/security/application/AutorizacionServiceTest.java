package backend.security.application;

import backend.security.application.port.UsuarioInstitucionRepository;
import backend.security.application.port.UsuarioRepository;
import backend.security.domain.model.TipoContextoUsuario;
import backend.security.domain.model.Usuario;
import backend.security.domain.model.UsuarioInstitucion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de {@link AutorizacionService}: control de acceso por rol,
 * detección de super administrador y aislamiento multiinstitución. Los repositorios
 * están simulados con Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AutorizacionService – roles y aislamiento por institución")
class AutorizacionServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioInstitucionRepository usuarioInstitucionRepository;

    @InjectMocks private AutorizacionService autorizacionService;

    private Usuario usuario(UUID id, TipoContextoUsuario ctx) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setTipoContexto(ctx);
        return u;
    }

    private UsuarioInstitucion vinculo(UUID usuarioId, UUID institucionId, boolean principal) {
        UsuarioInstitucion v = new UsuarioInstitucion();
        v.setUsuarioId(usuarioId);
        v.setInstitucionId(institucionId);
        v.setEsPrincipal(principal);
        return v;
    }

    @Test
    @DisplayName("esSuperAdmin: verdadero para contexto PLATAFORMA sin rol VISITANTE")
    void esSuperAdminVerdadero() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario(id, TipoContextoUsuario.PLATAFORMA)));
        when(usuarioRepository.findRolesByUsuarioId(id)).thenReturn(List.of("SUPER_ADMIN"));

        assertThat(autorizacionService.esSuperAdmin(id)).isTrue();
    }

    @Test
    @DisplayName("esSuperAdmin: falso si tiene rol VISITANTE aunque sea de PLATAFORMA")
    void esSuperAdminFalsoPorVisitante() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario(id, TipoContextoUsuario.PLATAFORMA)));
        when(usuarioRepository.findRolesByUsuarioId(id)).thenReturn(List.of("VISITANTE"));

        assertThat(autorizacionService.esSuperAdmin(id)).isFalse();
    }

    @Test
    @DisplayName("esSuperAdmin: falso si el contexto no es PLATAFORMA")
    void esSuperAdminFalsoPorContexto() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario(id, TipoContextoUsuario.INSTITUCION)));
        when(usuarioRepository.findRolesByUsuarioId(id)).thenReturn(List.of("ADMIN_INSTITUCION"));

        assertThat(autorizacionService.esSuperAdmin(id)).isFalse();
    }

    @Test
    @DisplayName("esSuperAdmin: falso si el usuario no existe")
    void esSuperAdminFalsoUsuarioInexistente() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThat(autorizacionService.esSuperAdmin(id)).isFalse();
    }

    @Test
    @DisplayName("exigirSuperAdmin lanza SecurityException cuando no lo es")
    void exigirSuperAdminFalla() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> autorizacionService.exigirSuperAdmin(id))
                .isInstanceOf(SecurityException.class);
    }

    @Test
    @DisplayName("tieneAlgunRol: verdadero si el usuario posee al menos uno de los roles pedidos")
    void tieneAlgunRol() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findRolesByUsuarioId(id)).thenReturn(List.of("DOCENTE"));

        assertThat(autorizacionService.tieneAlgunRol(id, "COORDINADOR_ACADEMICO", "DOCENTE")).isTrue();
        assertThat(autorizacionService.tieneAlgunRol(id, "ADMIN_INSTITUCION")).isFalse();
    }

    @Test
    @DisplayName("exigirRol lanza SecurityException si no tiene ninguno de los roles")
    void exigirRolFalla() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findRolesByUsuarioId(id)).thenReturn(List.of("ESTUDIANTE"));

        assertThatThrownBy(() -> autorizacionService.exigirRol(id, "ADMIN_INSTITUCION"))
                .isInstanceOf(SecurityException.class);
    }

    @Test
    @DisplayName("tieneRolEnInstitucion consulta los roles dentro del alcance de la institución")
    void tieneRolEnInstitucion() {
        UUID id = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        when(usuarioRepository.findRolesByUsuarioIdAndInstitucion(id, inst)).thenReturn(List.of("COORDINADOR_ACADEMICO"));

        assertThat(autorizacionService.tieneRolEnInstitucion(id, inst, "COORDINADOR_ACADEMICO")).isTrue();
        assertThat(autorizacionService.tieneRolEnInstitucion(id, inst, "ADMIN_INSTITUCION")).isFalse();
    }

    @Test
    @DisplayName("institucionConRol devuelve la institución principal cuando el rol es válido")
    void institucionConRolExitoso() {
        UUID id = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        when(usuarioInstitucionRepository.findByUsuarioId(id))
                .thenReturn(List.of(vinculo(id, inst, true)));
        when(usuarioRepository.findRolesByUsuarioIdAndInstitucion(id, inst))
                .thenReturn(List.of("ADMIN_INSTITUCION"));

        assertThat(autorizacionService.institucionConRol(id, "ADMIN_INSTITUCION")).isEqualTo(inst);
    }

    @Test
    @DisplayName("institucionConRol falla si el usuario no tiene institución principal")
    void institucionConRolSinPrincipal() {
        UUID id = UUID.randomUUID();
        when(usuarioInstitucionRepository.findByUsuarioId(id))
                .thenReturn(List.of(vinculo(id, UUID.randomUUID(), false)));

        assertThatThrownBy(() -> autorizacionService.institucionConRol(id, "ADMIN_INSTITUCION"))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining("institución");
    }

    @Test
    @DisplayName("Aislamiento: un rol válido en OTRA institución no autoriza sobre la principal")
    void aislamientoMultiinstitucion() {
        UUID id = UUID.randomUUID();
        UUID instPrincipal = UUID.randomUUID();
        // El usuario es principal en instPrincipal, pero allí NO tiene el rol exigido.
        when(usuarioInstitucionRepository.findByUsuarioId(id))
                .thenReturn(List.of(vinculo(id, instPrincipal, true)));
        when(usuarioRepository.findRolesByUsuarioIdAndInstitucion(id, instPrincipal))
                .thenReturn(List.of()); // sin roles en su institución principal

        assertThatThrownBy(() -> autorizacionService.institucionConRol(id, "ADMIN_INSTITUCION"))
                .isInstanceOf(SecurityException.class);
    }

    @Test
    @DisplayName("institucionDelAdmin exige el rol ADMIN_INSTITUCION sobre la institución principal")
    void institucionDelAdmin() {
        UUID id = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        when(usuarioInstitucionRepository.findByUsuarioId(id))
                .thenReturn(List.of(vinculo(id, inst, true)));
        when(usuarioRepository.findRolesByUsuarioIdAndInstitucion(id, inst))
                .thenReturn(List.of("ADMIN_INSTITUCION"));

        assertThatCode(() -> autorizacionService.institucionDelAdmin(id)).doesNotThrowAnyException();
        assertThat(autorizacionService.institucionDelAdmin(id)).isEqualTo(inst);
    }
}
