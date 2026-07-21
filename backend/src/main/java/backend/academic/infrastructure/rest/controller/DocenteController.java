package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.docente.*;
import backend.academic.infrastructure.rest.dto.Asistencia.GuardarAsistenciaRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/docente")
public class DocenteController {

    private final MisClasesUseCase misClases;
    private final MiHorarioUseCase miHorario;
    private final EstudiantesDeMiClaseUseCase estudiantes;
    private final ObtenerAsistenciaUseCase obtenerAsistencia;
    private final GuardarAsistenciaUseCase guardarAsistencia;
    private final ReporteAsistenciaUseCase reporte;

    public DocenteController(MisClasesUseCase misClases, MiHorarioUseCase miHorario,
            EstudiantesDeMiClaseUseCase estudiantes, ObtenerAsistenciaUseCase obtenerAsistencia, GuardarAsistenciaUseCase guardarAsistencia, ReporteAsistenciaUseCase reporte) {
        this.misClases = misClases;
        this.miHorario = miHorario;
        this.estudiantes = estudiantes;
        this.obtenerAsistencia = obtenerAsistencia;
        this.guardarAsistencia = guardarAsistencia;
        this.reporte = reporte;
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
            @org.springframework.web.bind.annotation.RequestParam
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            java.time.LocalDate fecha,
            @AuthenticationPrincipal UUID usuarioId) {
        try { return ResponseEntity.ok(obtenerAsistencia.ejecutar(usuarioId, cargaId, fecha)); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage())); }
    }

    @PostMapping("/clases/{cargaId}/asistencia")
    public ResponseEntity<?> guardarAsistencia(@PathVariable UUID cargaId,
            @jakarta.validation.Valid @RequestBody GuardarAsistenciaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try { guardarAsistencia.ejecutar(usuarioId, cargaId, req); return ResponseEntity.ok(new Msg("Asistencia guardada.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.CONFLICT).body(new Msg(e.getMessage())); }
    }

    @GetMapping("/clases/{cargaId}/reporte-asistencia")
    public ResponseEntity<?> reporteAsistencia(@PathVariable UUID cargaId, @AuthenticationPrincipal UUID usuarioId) {
        try { return ResponseEntity.ok(reporte.ejecutar(usuarioId, cargaId)); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage())); }
    }

    private ResponseEntity<?> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
    }

    record Msg(String mensaje) {
    }
}