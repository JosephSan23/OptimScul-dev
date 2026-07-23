package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.configAcademica.*;
import backend.academic.infrastructure.rest.dto.EscalaValorativaRequestDto;
import backend.academic.infrastructure.rest.dto.EscalaValorativaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/escalas")
public class EscalaValorativaController {
    private final ListarEscalasUseCase listar;
    private final ObtenerEscalaUseCase obtener;
    private final CrearEscalaUseCase crear;
    private final EditarEscalaUseCase editar;
    private final CambiarEstadoEscalaUseCase cambiarEstado;

    public EscalaValorativaController(ListarEscalasUseCase listar, ObtenerEscalaUseCase obtener,
            CrearEscalaUseCase crear, EditarEscalaUseCase editar, CambiarEstadoEscalaUseCase cambiarEstado) {
                this.listar = listar;
                this.obtener = obtener;
                this.crear = crear;
                this.editar = editar;
                this.cambiarEstado = cambiarEstado;
            }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID usuarioId) {
        return ResponseEntity.ok(
            listar.ejecutar(usuarioId)
                .stream()
                .map(EscalaValorativaResponseDto::desde)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(EscalaValorativaResponseDto.desde(obtener.ejecutar(usuarioId, id)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage())); }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EscalaValorativaRequestDto req, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(EscalaValorativaResponseDto.desde(crear.ejecutar(usuarioId, req)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return conflict(e); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody EscalaValorativaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(EscalaValorativaResponseDto.desde(editar.ejecutar(usuarioId, id, req)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.activar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Área activada.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.inactivar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Área inactivada.")); }
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