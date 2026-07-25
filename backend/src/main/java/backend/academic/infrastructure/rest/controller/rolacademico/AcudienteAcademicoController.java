package backend.academic.infrastructure.rest.controller.rolacademico;

import backend.academic.application.usecase.acudiente.ListarHijosUseCase;
import backend.academic.application.usecase.acudiente.NotasHijoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/acudiente")
public class AcudienteAcademicoController {

    private final ListarHijosUseCase listarHijos;
    private final NotasHijoUseCase notasHijo;

    public AcudienteAcademicoController(ListarHijosUseCase listarHijos, NotasHijoUseCase notasHijo) {
        this.listarHijos = listarHijos;
        this.notasHijo = notasHijo;
    }

    @GetMapping("/hijos")
    public ResponseEntity<?> hijos(@AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listarHijos.ejecutar(usuarioId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/hijos/{estudianteId}/notas")
    public ResponseEntity<?> notasHijo(@PathVariable UUID estudianteId,
            @RequestParam UUID anioId, @RequestParam UUID periodoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(notasHijo.ejecutar(usuarioId, estudianteId, anioId, periodoId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    record Msg(String mensaje) {
    }
}