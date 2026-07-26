package backend.academic.infrastructure.rest.controller.rolacademico;

import backend.academic.application.usecase.estudiante.MisNotasUseCase;
import backend.academic.application.service.ContextoEstudianteService;
import backend.academic.application.service.HorarioFamiliaService;
import backend.academic.application.service.AsistenciaFamiliaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/estudiante")
public class EstudianteAcademicoController {

    private final MisNotasUseCase misNotas;
    private final ContextoEstudianteService contexto;
    private final HorarioFamiliaService horario;
    private final AsistenciaFamiliaService asistencia;

    public EstudianteAcademicoController(MisNotasUseCase misNotas, ContextoEstudianteService contexto, 
        HorarioFamiliaService horario, AsistenciaFamiliaService asistencia) {
        this.misNotas = misNotas;
        this.contexto = contexto;
        this.horario = horario;
        this.asistencia = asistencia;
    }

    @GetMapping("/mis-notas")
    public ResponseEntity<?> misNotas(@RequestParam UUID anioId, @RequestParam UUID periodoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try { return ResponseEntity.ok(misNotas.ejecutar(usuarioId, anioId, periodoId)); }
        catch (SecurityException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage())); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage())); }
    }

    @GetMapping("/mi-horario")
    public ResponseEntity<?> miHorario(@RequestParam UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try { return ResponseEntity.ok(horario.calcular(contexto.resolver(usuarioId).estudianteId(), anioId)); }
        catch (SecurityException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage())); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage())); }
    }

    @GetMapping("/mi-asistencia")
    public ResponseEntity<?> miAsistencia(@RequestParam UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try { return ResponseEntity.ok(asistencia.calcular(contexto.resolver(usuarioId).estudianteId(), anioId)); }
        catch (SecurityException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage())); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage())); }
    }

    record Msg(String mensaje) {}
}