package backend.config.infrastructure.rest.controller;

import backend.config.application.usecase.AnioLectivo.*;
import backend.config.infrastructure.rest.dto.AnioLectivoConfigRequestDto;
import backend.config.infrastructure.rest.dto.AnioLectivoConfigResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/config/anios-lectivos")
public class AnioLectivoController {

    private final ListarAniosLectivosUseCase listar;
    private final ObtenerAnioLectivoUseCase obtener;
    private final CrearAnioLectivoUseCase crear;
    private final EditarAnioLectivoUseCase editar;
    private final MarcarAnioActualUseCase marcarActual;

    public AnioLectivoController(ListarAniosLectivosUseCase listar, ObtenerAnioLectivoUseCase obtener,
            CrearAnioLectivoUseCase crear, EditarAnioLectivoUseCase editar,
            MarcarAnioActualUseCase marcarActual) {
        this.listar = listar;
        this.obtener = obtener;
        this.crear = crear;
        this.editar = editar;
        this.marcarActual = marcarActual;
    }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity
                    .ok(listar.ejecutar(adminId).stream().map(AnioLectivoConfigResponseDto::desde).toList());
        } catch (SecurityException e) {
            return forbidden(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(AnioLectivoConfigResponseDto.desde(obtener.ejecutar(adminId, id)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody AnioLectivoConfigRequestDto req,
            @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AnioLectivoConfigResponseDto.desde(crear.ejecutar(adminId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody AnioLectivoConfigRequestDto req,
            @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(AnioLectivoConfigResponseDto.desde(editar.ejecutar(adminId, id, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @PatchMapping("/{id}/marcar-actual")
    public ResponseEntity<?> marcarActual(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try {
            marcarActual.ejecutar(adminId, id);
            return ResponseEntity.ok(new MensajeResponse("Año marcado como actual."));
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