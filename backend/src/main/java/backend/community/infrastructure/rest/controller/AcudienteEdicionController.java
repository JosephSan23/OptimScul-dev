package backend.community.infrastructure.rest.controller;

import backend.community.application.usecase.AcudienteCrud.DesvincularAcudienteUseCase;
import backend.community.application.usecase.AcudienteCrud.EditarAcudienteUseCase;
import backend.community.application.usecase.AcudienteCrud.ObtenerAcudienteUseCase;
import backend.community.infrastructure.rest.dto.EditarAcudienteRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/community/acudientes/vinculo")
public class AcudienteEdicionController {

    private final ObtenerAcudienteUseCase obtener;
    private final EditarAcudienteUseCase editar;
    private final DesvincularAcudienteUseCase desvincular;

    public AcudienteEdicionController(ObtenerAcudienteUseCase obtener, EditarAcudienteUseCase editar,
            DesvincularAcudienteUseCase desvincular) {
        this.obtener = obtener;
        this.editar = editar;
        this.desvincular = desvincular;
    }

    @GetMapping("/{vinculoId}")
    public ResponseEntity<?> obtener(@PathVariable UUID vinculoId, @AuthenticationPrincipal UUID coordId) {
        try {
            return ResponseEntity.ok(obtener.ejecutar(coordId, vinculoId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping("/{vinculoId}")
    public ResponseEntity<?> editar(@PathVariable UUID vinculoId, @Valid @RequestBody EditarAcudienteRequestDto req,
            @AuthenticationPrincipal UUID coordId) {
        try {
            editar.ejecutar(coordId, vinculoId, req);
            return ResponseEntity.ok(new MensajeResponse("Acudiente actualizado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{vinculoId}")
    public ResponseEntity<?> desvincular(@PathVariable UUID vinculoId, @AuthenticationPrincipal UUID coordId) {
        try {
            desvincular.ejecutar(coordId, vinculoId);
            return ResponseEntity.ok(new MensajeResponse("Acudiente desvinculado del estudiante."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {
    }
}