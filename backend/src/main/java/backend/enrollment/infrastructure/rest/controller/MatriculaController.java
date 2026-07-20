package backend.enrollment.infrastructure.rest.controller;

import backend.enrollment.application.usecase.*;
import backend.enrollment.infrastructure.rest.dto.MatriculaRequestDto;
import backend.enrollment.infrastructure.rest.dto.MatriculaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/matriculas")
public class MatriculaController {

    private final ListarMatriculasPorAnioUseCase listar;
    private final ObtenerMatriculaUseCase obtener;
    private final CrearMatriculaUseCase crear;
    private final AsignarGrupoUseCase asignarGrupo;
    private final CambiarEstadoMatriculaUseCase cambiarEstado;

    public MatriculaController(ListarMatriculasPorAnioUseCase listar, ObtenerMatriculaUseCase obtener,
            CrearMatriculaUseCase crear, AsignarGrupoUseCase asignarGrupo,
            CambiarEstadoMatriculaUseCase cambiarEstado) {
        this.listar = listar;
        this.obtener = obtener;
        this.crear = crear;
        this.asignarGrupo = asignarGrupo;
        this.cambiarEstado = cambiarEstado;
    }

    @GetMapping("/por-anio/{anioId}")
    public ResponseEntity<?> listar(@PathVariable UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(usuarioId, anioId));
        } catch (SecurityException e) {
            return forbidden(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(MatriculaResponseDto.desde(obtener.ejecutar(usuarioId, id)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MatriculaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MatriculaResponseDto.desde(crear.ejecutar(usuarioId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/asignar-grupo/{grupoId}")
    public ResponseEntity<?> asignarGrupo(@PathVariable UUID id, @PathVariable UUID grupoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(MatriculaResponseDto.desde(asignarGrupo.ejecutar(usuarioId, id, grupoId)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/retirar")
    public ResponseEntity<?> retirar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstado.retirar(usuarioId, id);
            return ResponseEntity.ok(new MensajeResponse("Matrícula retirada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstado.cancelar(usuarioId, id);
            return ResponseEntity.ok(new MensajeResponse("Matrícula cancelada."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstado.reactivar(usuarioId, id);
            return ResponseEntity.ok(new MensajeResponse("Matrícula reactivada."));
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