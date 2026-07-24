package backend.academic.infrastructure.rest.controller.docente;

import backend.academic.application.usecase.docente.*;
import backend.academic.application.usecase.actividad.calificacion.*;
import backend.academic.application.usecase.actividad.*;
import backend.academic.infrastructure.rest.dto.Asistencia.GuardarAsistenciaRequestDto;
import backend.academic.infrastructure.rest.dto.Calificacion.GuardarCalificacionesRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/docente")
public class DocenteController {

    private final MisClasesUseCase misClases;
    private final MiHorarioUseCase miHorario;
    private final EstudiantesDeMiClaseUseCase estudiantes;
    private final ObtenerAsistenciaUseCase obtenerAsistencia;
    private final GuardarAsistenciaUseCase guardarAsistencia;
    private final ReporteAsistenciaUseCase reporte;
    private final MatrizAsistenciaDocenteUseCase matrizDocente;
    private final ListarActividadesUseCase listarActividades;
    private final ObtenerActividadUseCase obtenerActividad;
    private final CrearActividadUseCase crearActividad;
    private final EditarActividadUseCase editarActividad;
    private final EliminarActividadUseCase eliminarActividad;
    private final CambiarEstadoActividadUseCase cambiarEstadoActividad;
    private final ObtenerCalificacionesUseCase obtenerCalificaciones;
    private final GuardarCalificacionesUseCase guardarCalificaciones;

    public DocenteController(MisClasesUseCase misClases, MiHorarioUseCase miHorario,
            EstudiantesDeMiClaseUseCase estudiantes, ObtenerAsistenciaUseCase obtenerAsistencia,
            GuardarAsistenciaUseCase guardarAsistencia, ReporteAsistenciaUseCase reporte,
            MatrizAsistenciaDocenteUseCase matrizDocente, ListarActividadesUseCase listarActividades,
            ObtenerActividadUseCase obtenerActividad, CrearActividadUseCase crearActividad,
            EditarActividadUseCase editarActividad, CambiarEstadoActividadUseCase cambiarEstadoActividad,
            ObtenerCalificacionesUseCase obtenerCalificaciones, GuardarCalificacionesUseCase guardarCalificaciones,
            EliminarActividadUseCase eliminarActividad) {
        this.misClases = misClases;
        this.miHorario = miHorario;
        this.estudiantes = estudiantes;
        this.obtenerAsistencia = obtenerAsistencia;
        this.guardarAsistencia = guardarAsistencia;
        this.reporte = reporte;
        this.matrizDocente = matrizDocente;
        this.listarActividades = listarActividades;
        this.obtenerActividad = obtenerActividad;
        this.crearActividad = crearActividad;
        this.editarActividad = editarActividad;
        this.cambiarEstadoActividad = cambiarEstadoActividad;
        this.obtenerCalificaciones = obtenerCalificaciones;
        this.guardarCalificaciones = guardarCalificaciones;
        this.eliminarActividad = eliminarActividad;
    }

    @GetMapping("/mis-clases/{anioId}")
    public ResponseEntity<?> misClases(@PathVariable UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(misClases.ejecutar(usuarioId, anioId));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/mi-horario/{anioId}")
    public ResponseEntity<?> miHorario(@PathVariable UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(miHorario.ejecutar(usuarioId, anioId));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/clases/{cargaId}/estudiantes")
    public ResponseEntity<?> estudiantes(@PathVariable UUID cargaId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(estudiantes.ejecutar(usuarioId, cargaId));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/clases/{cargaId}/asistencia")
    public ResponseEntity<?> obtenerAsistencia(@PathVariable UUID cargaId,
            @org.springframework.web.bind.annotation.RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate fecha,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(obtenerAsistencia.ejecutar(usuarioId, cargaId, fecha));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @PostMapping("/clases/{cargaId}/asistencia")
    public ResponseEntity<?> guardarAsistencia(@PathVariable UUID cargaId,
            @jakarta.validation.Valid @RequestBody GuardarAsistenciaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            guardarAsistencia.ejecutar(usuarioId, cargaId, req);
            return ResponseEntity.ok(new Msg("Asistencia guardada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/clases/{cargaId}/reporte-asistencia")
    public ResponseEntity<?> reporteAsistencia(@PathVariable UUID cargaId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(reporte.ejecutar(usuarioId, cargaId));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/clases/{cargaId}/matriz-asistencia")
    public ResponseEntity<?> matriz(@PathVariable UUID cargaId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(matrizDocente.ejecutar(usuarioId, cargaId));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/clases/{cargaId}/actividades")
    public ResponseEntity<?> actividades(@PathVariable UUID cargaId,
            @org.springframework.web.bind.annotation.RequestParam UUID periodoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listarActividades.ejecutar(usuarioId, cargaId, periodoId)
                    .stream().map(backend.academic.infrastructure.rest.dto.ActividadAcademicaResponseDto::desde)
                    .toList());
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/actividades/{id}")
    public ResponseEntity<?> obtenerActividad(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(backend.academic.infrastructure.rest.dto.ActividadAcademicaResponseDto
                    .desde(obtenerActividad.ejecutar(usuarioId, id)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @PostMapping("/clases/{cargaId}/actividades")
    public ResponseEntity<?> crearActividad(@PathVariable UUID cargaId,
            @jakarta.validation.Valid @RequestBody backend.academic.infrastructure.rest.dto.ActividadAcademicaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(backend.academic.infrastructure.rest.dto.ActividadAcademicaResponseDto
                            .desde(crearActividad.ejecutar(usuarioId, cargaId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Msg(e.getMessage()));
        }
    }

    @PutMapping("/actividades/{id}")
    public ResponseEntity<?> editarActividad(@PathVariable UUID id,
            @jakarta.validation.Valid @RequestBody backend.academic.infrastructure.rest.dto.ActividadAcademicaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(backend.academic.infrastructure.rest.dto.ActividadAcademicaResponseDto
                    .desde(editarActividad.ejecutar(usuarioId, id, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Msg(e.getMessage()));
        }
    }

    @PatchMapping("/actividades/{id}/publicar")
    public ResponseEntity<?> publicarActividad(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstadoActividad.publicar(usuarioId, id);
            return ResponseEntity.ok(new Msg("Actividad publicada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/actividades/{id}/cerrar")
    public ResponseEntity<?> cerrarActividad(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstadoActividad.cerrar(usuarioId, id);
            return ResponseEntity.ok(new Msg("Actividad cerrada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/actividades/{id}/anular")
    public ResponseEntity<?> anularActividad(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstadoActividad.anular(usuarioId, id);
            return ResponseEntity.ok(new Msg("Actividad anulada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @DeleteMapping("/actividades/{id}")
    public ResponseEntity<?> eliminarActividad(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            eliminarActividad.ejecutar(usuarioId, id);
            return ResponseEntity.ok(new Msg("Actividad eliminada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @GetMapping("/actividades/{id}/calificaciones")
    public ResponseEntity<?> calificaciones(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(obtenerCalificaciones.ejecutar(usuarioId, id));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @PostMapping("/actividades/{id}/calificaciones")
    public ResponseEntity<?> guardarCalificaciones(@PathVariable UUID id,
            @Valid @RequestBody GuardarCalificacionesRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            guardarCalificaciones.ejecutar(usuarioId, id, req);
            return ResponseEntity.ok(new Msg("Calificaciones guardadas."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    private ResponseEntity<?> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
    }

    private ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Msg(e.getMessage()));
    }

    record Msg(String mensaje) {
    }
}