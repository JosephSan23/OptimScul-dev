package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.reporte.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/reportes")
public class ReporteController {

    private final MatrizAsistenciaCoordUseCase matrizCoord;

    public ReporteController(
            MatrizAsistenciaCoordUseCase matrizCoord) {
        this.matrizCoord = matrizCoord;
    }

    @GetMapping("/matriz-asistencia/{cargaId}")
    public ResponseEntity<?> matriz(@PathVariable UUID cargaId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(matrizCoord.ejecutar(usuarioId, cargaId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    record Msg(String mensaje) {
    }
}