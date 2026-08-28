package backend.community.application.usecase.EstudianteCrud;

import backend.community.infrastructure.rest.dto.EstudianteRequestDto;
import backend.onboarding.application.AltaUsuarioInstitucionService;
import backend.people.application.port.EstudianteRepository;
import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.EstadoEstudiante;
import backend.people.domain.model.Estudiante;
import backend.people.domain.model.Institucion;
import backend.people.domain.model.Persona;
import backend.security.application.AutorizacionService;
import backend.security.domain.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de {@link CrearEstudianteUseCase}: creación de la cuenta de estudiante
 * y generación del código consecutivo por institución. Las dependencias (servicio de alta,
 * repositorios y autorización) están simuladas con Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CrearEstudianteUseCase – alta de estudiante y código consecutivo")
class CrearEstudianteUseCaseTest {

    @Mock private AltaUsuarioInstitucionService altaUsuario;
    @Mock private EstudianteRepository estudianteRepository;
    @Mock private InstitucionRepository institucionRepository;
    @Mock private AutorizacionService auth;

    @InjectMocks private CrearEstudianteUseCase useCase;

    private EstudianteRequestDto dto() {
        EstudianteRequestDto d = new EstudianteRequestDto();
        d.setTipoDocumento("TI");
        d.setNumeroDocumento("1234567890");
        d.setPrimerNombre("Ana");
        d.setPrimerApellido("Gomez");
        d.setCorreo("ana@correo.com");
        return d;
    }

    private AltaUsuarioInstitucionService.Resultado cuentaConUsername(String username) {
        Persona persona = mock(Persona.class);
        when(persona.getId()).thenReturn(UUID.randomUUID());
        Usuario usuario = mock(Usuario.class);
        when(usuario.getUsername()).thenReturn(username);
        return new AltaUsuarioInstitucionService.Resultado(persona, usuario);
    }

    private Institucion institucion(String codigo) {
        Institucion i = new Institucion();
        i.setCodigo(codigo);
        return i;
    }

    @Test
    @DisplayName("Crea el estudiante y genera el código {codigoColegio}-0001 para el primero")
    void creaEstudianteYGeneraPrimerCodigo() {
        UUID adminId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();

        when(auth.institucionConRol(any(), any())).thenReturn(inst);
        AltaUsuarioInstitucionService.Resultado cuenta = cuentaConUsername("ana-gomez12");
        when(altaUsuario.provisionar(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(cuenta);
        when(institucionRepository.findById(inst)).thenReturn(Optional.of(institucion("COL")));
        when(estudianteRepository.findByInstitucionId(inst)).thenReturn(List.of()); // aún no hay estudiantes

        CrearEstudianteUseCase.Resultado res = useCase.ejecutar(adminId, dto());

        assertThat(res.username()).isEqualTo("ana-gomez12");
        assertThat(res.codigoEstudiante()).isEqualTo("COL-0001");

        ArgumentCaptor<Estudiante> captor = ArgumentCaptor.forClass(Estudiante.class);
        verify(estudianteRepository).save(captor.capture());
        Estudiante guardado = captor.getValue();
        assertThat(guardado.getInstitucionId()).isEqualTo(inst);
        assertThat(guardado.getEstado()).isEqualTo(EstadoEstudiante.ACTIVO);
        assertThat(guardado.getFechaIngreso()).isEqualTo(LocalDate.now()); // default: hoy
        assertThat(guardado.getCodigoEstudiante()).isEqualTo("COL-0001");
    }

    @Test
    @DisplayName("Usa base 'EST' y consecutivo correcto cuando la institución no tiene código")
    void generaCodigoConBasePorDefecto() {
        UUID adminId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();

        when(auth.institucionConRol(any(), any())).thenReturn(inst);
        AltaUsuarioInstitucionService.Resultado cuenta = cuentaConUsername("ana-gomez12");
        when(altaUsuario.provisionar(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(cuenta);
        when(institucionRepository.findById(inst)).thenReturn(Optional.of(institucion(null)));
        // Ya existen 2 estudiantes → el nuevo es el 3.º
        when(estudianteRepository.findByInstitucionId(inst))
                .thenReturn(List.of(new Estudiante(), new Estudiante()));

        CrearEstudianteUseCase.Resultado res = useCase.ejecutar(adminId, dto());

        assertThat(res.codigoEstudiante()).isEqualTo("EST-0003");
    }

    @Test
    @DisplayName("Respeta la fecha de ingreso enviada en el DTO")
    void respetaFechaDeIngreso() {
        UUID adminId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        LocalDate fecha = LocalDate.of(2026, 1, 15);
        EstudianteRequestDto d = dto();
        d.setFechaIngreso(fecha);

        when(auth.institucionConRol(any(), any())).thenReturn(inst);
        AltaUsuarioInstitucionService.Resultado cuenta = cuentaConUsername("ana-gomez12");
        when(altaUsuario.provisionar(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(cuenta);
        when(institucionRepository.findById(inst)).thenReturn(Optional.of(institucion("COL")));
        when(estudianteRepository.findByInstitucionId(inst)).thenReturn(List.of());

        useCase.ejecutar(adminId, d);

        ArgumentCaptor<Estudiante> captor = ArgumentCaptor.forClass(Estudiante.class);
        verify(estudianteRepository).save(captor.capture());
        assertThat(captor.getValue().getFechaIngreso()).isEqualTo(fecha);
    }

    @Test
    @DisplayName("Falla si la institución no existe y no guarda el estudiante")
    void institucionNoExiste() {
        UUID adminId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();

        when(auth.institucionConRol(any(), any())).thenReturn(inst);
        // Sin stubbear getId()/getUsername(): el flujo falla en findById antes de leerlos.
        when(altaUsuario.provisionar(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new AltaUsuarioInstitucionService.Resultado(mock(Persona.class), mock(Usuario.class)));
        when(institucionRepository.findById(inst)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.ejecutar(adminId, dto()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("institución no existe");

        verify(estudianteRepository, never()).save(any());
    }
}
