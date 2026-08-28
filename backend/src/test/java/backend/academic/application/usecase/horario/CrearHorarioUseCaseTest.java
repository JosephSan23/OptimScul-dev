package backend.academic.application.usecase.horario;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.Horario.HorarioConsultaRepository;
import backend.academic.application.port.HorarioCargaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.academic.domain.model.DiaSemana;
import backend.academic.domain.model.EstadoCargaAcademica;
import backend.academic.domain.model.HorarioCarga;
import backend.academic.infrastructure.rest.dto.Horario.HorarioCargaRequestDto;
import backend.security.application.AutorizacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de {@link CrearHorarioUseCase}: reglas para programar una franja
 * horaria sin cruces. Cubre la validación de la franja (inicio &lt; fin), la pertenencia
 * de la carga a la institución del usuario (aislamiento multiinstitución), el estado de
 * la carga y la detección de choques de grupo y de profesor. Todas las dependencias
 * están simuladas con Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CrearHorarioUseCase – programación de horarios sin cruces")
class CrearHorarioUseCaseTest {

    @Mock private HorarioCargaRepository horarioRepo;
    @Mock private CargaAcademicaRepository cargaRepo;
    @Mock private HorarioConsultaRepository consulta;
    @Mock private AutorizacionService auth;

    @InjectMocks private CrearHorarioUseCase useCase;

    private CargaAcademica cargaActiva(UUID institucionId) {
        CargaAcademica c = new CargaAcademica();
        c.setId(UUID.randomUUID());
        c.setInstitucionId(institucionId);
        c.setAnioLectivoId(UUID.randomUUID());
        c.setProfesorId(UUID.randomUUID());
        c.setGrupoId(UUID.randomUUID());
        c.setEstado(EstadoCargaAcademica.ACTIVA);
        return c;
    }

    private HorarioCargaRequestDto dto(UUID cargaId, LocalTime inicio, LocalTime fin) {
        HorarioCargaRequestDto d = new HorarioCargaRequestDto();
        d.setCargaAcademicaId(cargaId);
        d.setDiaSemana(DiaSemana.LUNES);
        d.setHoraInicio(inicio);
        d.setHoraFin(fin);
        d.setAula("201");
        return d;
    }

    // ───────────────────── validarFranja (lógica estática pura) ─────────────────────

    @Test
    @DisplayName("validarFranja acepta una franja con inicio anterior al fin")
    void validarFranjaValida() {
        HorarioCargaRequestDto d = dto(UUID.randomUUID(), LocalTime.of(7, 0), LocalTime.of(8, 0));
        // No debe lanzar excepción.
        CrearHorarioUseCase.validarFranja(d);
    }

    @Test
    @DisplayName("validarFranja rechaza inicio igual o posterior al fin")
    void validarFranjaInvalida() {
        HorarioCargaRequestDto igual = dto(UUID.randomUUID(), LocalTime.of(8, 0), LocalTime.of(8, 0));
        assertThatThrownBy(() -> CrearHorarioUseCase.validarFranja(igual))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("anterior a la hora de fin");

        HorarioCargaRequestDto invertida = dto(UUID.randomUUID(), LocalTime.of(9, 0), LocalTime.of(8, 0));
        assertThatThrownBy(() -> CrearHorarioUseCase.validarFranja(invertida))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("anterior a la hora de fin");
    }

    // ───────────────────────────── ejecutar (flujo completo) ─────────────────────────

    @Test
    @DisplayName("Crea el horario cuando no hay choques y la carga está activa")
    void creaHorarioExitoso() {
        UUID usuarioId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        CargaAcademica carga = cargaActiva(inst);
        HorarioCargaRequestDto d = dto(carga.getId(), LocalTime.of(7, 0), LocalTime.of(8, 0));

        when(auth.institucionConRol(any(), any(), any())).thenReturn(inst);
        when(cargaRepo.findById(carga.getId())).thenReturn(Optional.of(carga));
        when(consulta.existeChoqueGrupo(any(), any(), any(), any(), any(), any())).thenReturn(false);
        when(consulta.existeChoqueProfesor(any(), any(), any(), any(), any(), any())).thenReturn(false);
        when(horarioRepo.save(any(HorarioCarga.class))).thenAnswer(inv -> inv.getArgument(0));

        HorarioCarga resultado = useCase.ejecutar(usuarioId, d);

        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getInstitucionId()).isEqualTo(inst);
        assertThat(resultado.getCargaAcademicaId()).isEqualTo(carga.getId());
        assertThat(resultado.getDiaSemana()).isEqualTo(DiaSemana.LUNES);
        assertThat(resultado.getHoraInicio()).isEqualTo(LocalTime.of(7, 0));
        assertThat(resultado.getHoraFin()).isEqualTo(LocalTime.of(8, 0));
        assertThat(resultado.getActivo()).isTrue();
        verify(horarioRepo).save(any(HorarioCarga.class));
    }

    @Test
    @DisplayName("Falla si la carga académica no existe")
    void cargaNoExiste() {
        UUID usuarioId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        UUID cargaId = UUID.randomUUID();
        HorarioCargaRequestDto d = dto(cargaId, LocalTime.of(7, 0), LocalTime.of(8, 0));

        when(auth.institucionConRol(any(), any(), any())).thenReturn(inst);
        when(cargaRepo.findById(cargaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.ejecutar(usuarioId, d))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe");

        verify(horarioRepo, never()).save(any());
    }

    @Test
    @DisplayName("Aislamiento: rechaza programar sobre una carga de otra institución")
    void cargaDeOtraInstitucion() {
        UUID usuarioId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        CargaAcademica cargaAjena = cargaActiva(UUID.randomUUID()); // institución distinta
        HorarioCargaRequestDto d = dto(cargaAjena.getId(), LocalTime.of(7, 0), LocalTime.of(8, 0));

        when(auth.institucionConRol(any(), any(), any())).thenReturn(inst);
        when(cargaRepo.findById(cargaAjena.getId())).thenReturn(Optional.of(cargaAjena));

        assertThatThrownBy(() -> useCase.ejecutar(usuarioId, d))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining("no pertenece a tu institución");

        verify(horarioRepo, never()).save(any());
    }

    @Test
    @DisplayName("Rechaza programar horarios de una carga que no está activa")
    void cargaNoActiva() {
        UUID usuarioId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        CargaAcademica carga = cargaActiva(inst);
        carga.setEstado(EstadoCargaAcademica.INACTIVA);
        HorarioCargaRequestDto d = dto(carga.getId(), LocalTime.of(7, 0), LocalTime.of(8, 0));

        when(auth.institucionConRol(any(), any(), any())).thenReturn(inst);
        when(cargaRepo.findById(carga.getId())).thenReturn(Optional.of(carga));

        assertThatThrownBy(() -> useCase.ejecutar(usuarioId, d))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("cargas activas");

        verify(horarioRepo, never()).save(any());
    }

    @Test
    @DisplayName("Detecta choque de grupo: el grupo ya tiene clase en esa franja")
    void choqueDeGrupo() {
        UUID usuarioId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        CargaAcademica carga = cargaActiva(inst);
        HorarioCargaRequestDto d = dto(carga.getId(), LocalTime.of(7, 0), LocalTime.of(8, 0));

        when(auth.institucionConRol(any(), any(), any())).thenReturn(inst);
        when(cargaRepo.findById(carga.getId())).thenReturn(Optional.of(carga));
        when(consulta.existeChoqueGrupo(any(), any(), any(), any(), any(), any())).thenReturn(true);

        assertThatThrownBy(() -> useCase.ejecutar(usuarioId, d))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("grupo ya tiene");

        verify(horarioRepo, never()).save(any());
    }

    @Test
    @DisplayName("Detecta choque de profesor: el docente ya tiene clase en esa franja")
    void choqueDeProfesor() {
        UUID usuarioId = UUID.randomUUID();
        UUID inst = UUID.randomUUID();
        CargaAcademica carga = cargaActiva(inst);
        HorarioCargaRequestDto d = dto(carga.getId(), LocalTime.of(7, 0), LocalTime.of(8, 0));

        when(auth.institucionConRol(any(), any(), any())).thenReturn(inst);
        when(cargaRepo.findById(carga.getId())).thenReturn(Optional.of(carga));
        when(consulta.existeChoqueGrupo(any(), any(), any(), any(), any(), any())).thenReturn(false);
        when(consulta.existeChoqueProfesor(any(), any(), any(), any(), any(), any())).thenReturn(true);

        assertThatThrownBy(() -> useCase.ejecutar(usuarioId, d))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("profesor ya tiene");

        verify(horarioRepo, never()).save(any());
    }
}