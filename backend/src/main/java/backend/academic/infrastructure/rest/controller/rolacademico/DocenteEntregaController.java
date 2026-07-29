package backend.academic.infrastructure.rest.controller.rolacademico;

import backend.academic.application.usecase.actividad.entrega.ListarEntregasActividadUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/docente/actividades")
public class DocenteEntregaController {

    private final ListarEntregasActividadUseCase listarEntregas;

    public DocenteEntregaController(ListarEntregasActividadUseCase listarEntregas) {
        this.listarEntregas = listarEntregas;
    }

    @GetMapping("/{actividadId}/entregas")
    public ResponseEntity<?> entregas(@PathVariable UUID actividadId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listarEntregas.ejecutar(usuarioId, actividadId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    record Msg(String mensaje) {}
    
}
