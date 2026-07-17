package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.asignatura.*;
import backend.academic.infrastructure.rest.dto.Asignatura.AsignaturaRequestDto;
import backend.academic.infrastructure.rest.dto.Asignatura.AsignaturaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/asignaturas")
public class AsignaturaController {

    private final ListarAsignaturasUseCase listar;
    private final ObtenerAsignaturaUseCase obtener;
    private final CrearAsignaturaUseCase crear;
    private final EditarAsignaturaUseCase editar;
    private final CambiarEstadoAsignaturaUseCase cambiarEstado;

    public AsignaturaController(ListarAsignaturasUseCase listar, ObtenerAsignaturaUseCase obtener,
            CrearAsignaturaUseCase crear, EditarAsignaturaUseCase editar,
            CambiarEstadoAsignaturaUseCase cambiarEstado) {
        this.listar = listar;
        this.obtener = obtener;
        this.crear = crear;
        this.editar = editar;
        this.cambiarEstado = cambiarEstado;
    }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(usuarioId).stream()
                    .map(r -> AsignaturaResponseDto.desde(r.asignatura(), r.areaNombre())).toList());
        } catch (SecurityException e) {
            return forbidden(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(AsignaturaResponseDto.desde(obtener.ejecutar(usuarioId, id)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody AsignaturaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AsignaturaResponseDto.desde(crear.ejecutar(usuarioId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody AsignaturaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(AsignaturaResponseDto.desde(editar.ejecutar(usuarioId, id, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstado.activar(usuarioId, id);
            return ResponseEntity.ok(new MensajeResponse("Asignatura activada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstado.inactivar(usuarioId, id);
            return ResponseEntity.ok(new MensajeResponse("Asignatura inactivada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    private ResponseEntity<?> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
    }

    private ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
    }

    record MensajeResponse(String mensaje) {
    }
}