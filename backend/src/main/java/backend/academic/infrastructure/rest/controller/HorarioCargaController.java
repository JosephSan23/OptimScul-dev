package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.horario.*;
import backend.academic.infrastructure.rest.dto.Horario.HorarioCargaRequestDto;
import backend.academic.infrastructure.rest.dto.Horario.HorarioCargaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/horarios")
public class HorarioCargaController {

    private final ListarHorariosPorGrupoUseCase listar;
    private final ObtenerHorarioUseCase obtener;
    private final CrearHorarioUseCase crear;
    private final EditarHorarioUseCase editar;
    private final CambiarEstadoHorarioUseCase cambiarEstado;

    public HorarioCargaController(ListarHorariosPorGrupoUseCase listar, ObtenerHorarioUseCase obtener,
            CrearHorarioUseCase crear, EditarHorarioUseCase editar, CambiarEstadoHorarioUseCase cambiarEstado) {
        this.listar = listar;
        this.obtener = obtener;
        this.crear = crear;
        this.editar = editar;
        this.cambiarEstado = cambiarEstado;
    }

    @GetMapping("/por-grupo/{grupoId}")
    public ResponseEntity<?> listar(@PathVariable UUID grupoId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(usuarioId, grupoId));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage())); }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(HorarioCargaResponseDto.desde(obtener.ejecutar(usuarioId, id)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage())); }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody HorarioCargaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(HorarioCargaResponseDto.desde(crear.ejecutar(usuarioId, req)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return conflict(e); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody HorarioCargaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(HorarioCargaResponseDto.desde(editar.ejecutar(usuarioId, id, req)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.activar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Horario activado.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.inactivar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Horario inactivado.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    private ResponseEntity<?> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
    }
    private ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
    }
    record MensajeResponse(String mensaje) {}
}