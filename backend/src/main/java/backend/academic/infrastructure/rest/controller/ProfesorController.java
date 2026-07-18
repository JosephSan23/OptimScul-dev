package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.carga.ListarProfesoresUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/profesores")
public class ProfesorController {

    private final ListarProfesoresUseCase listar;

    public ProfesorController(ListarProfesoresUseCase listar) { this.listar = listar; }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(usuarioId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {}
}