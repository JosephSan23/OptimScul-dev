package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.grupo.*;
import backend.academic.infrastructure.rest.dto.GrupoRequestDto;
import backend.academic.infrastructure.rest.dto.GrupoResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/grupos")
public class GrupoController {

    private final ListarGruposPorGradoUseCase listar;
    private final ObtenerGrupoUseCase obtener;
    private final CrearGrupoUseCase crear;
    private final EditarGrupoUseCase editar;
    private final CambiarEstadoGrupoUseCase cambiarEstado;
    private final ListarGruposPorAnioUseCase listarPorAnio;

    public GrupoController(ListarGruposPorGradoUseCase listar, ObtenerGrupoUseCase obtener,
            CrearGrupoUseCase crear, EditarGrupoUseCase editar, CambiarEstadoGrupoUseCase cambiarEstado, ListarGruposPorAnioUseCase listarPorAnio) {
        this.listar = listar;
        this.obtener = obtener;
        this.crear = crear;
        this.editar = editar;
        this.cambiarEstado = cambiarEstado;
        this.listarPorAnio = listarPorAnio;
    }

    @GetMapping("/por-grado/{gradoId}")
    public ResponseEntity<?> listar(@PathVariable UUID gradoId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity
                    .ok(listar.ejecutar(usuarioId, gradoId).stream().map(GrupoResponseDto::desde).toList());
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping("/por-grado/{gradoId}")
    public ResponseEntity<?> crear(@PathVariable UUID gradoId, @Valid @RequestBody GrupoRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(GrupoResponseDto.desde(crear.ejecutar(usuarioId, gradoId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(GrupoResponseDto.desde(obtener.ejecutar(usuarioId, id)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody GrupoRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(GrupoResponseDto.desde(editar.ejecutar(usuarioId, id, req)));
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
            return ResponseEntity.ok(new MensajeResponse("Grupo activado."));
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
            return ResponseEntity.ok(new MensajeResponse("Grupo inactivado."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            cambiarEstado.cerrar(usuarioId, id);
            return ResponseEntity.ok(new MensajeResponse("Grupo cerrado."));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @GetMapping("/por-anio/{anioId}")
    public ResponseEntity<?> listarPorAnio(@PathVariable UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listarPorAnio.ejecutar(usuarioId, anioId).stream().map(GrupoResponseDto::desde).toList());
        } catch (SecurityException e) { return forbidden(e); }
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