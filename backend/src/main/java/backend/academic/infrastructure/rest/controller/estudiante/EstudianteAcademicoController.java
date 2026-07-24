package backend.academic.infrastructure.rest.controller.estudiante;

import backend.academic.application.usecase.estudiante.MisNotasUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/estudiante")
public class EstudianteAcademicoController {

    private final MisNotasUseCase misNotas;

    public EstudianteAcademicoController(MisNotasUseCase misNotas) { this.misNotas = misNotas; }

    @GetMapping("/mis-notas")
    public ResponseEntity<?> misNotas(@RequestParam UUID anioId, @RequestParam UUID periodoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try { return ResponseEntity.ok(misNotas.ejecutar(usuarioId, anioId, periodoId)); }
        catch (SecurityException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage())); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage())); }
    }

    record Msg(String mensaje) {}
}