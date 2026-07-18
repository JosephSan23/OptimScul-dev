package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.carga.*;
import backend.academic.infrastructure.rest.dto.CargaAcademica.CargaAcademicaRequestDto;
import backend.academic.infrastructure.rest.dto.CargaAcademica.CargaAcademicaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/cargas")
public class CargaAcademicaController {

    private final ListarCargasPorAnioUseCase listar;
    private final CrearCargaUseCase crear;
    private final EditarCargaUseCase editar;
    private final CambiarEstadoCargaUseCase cambiarEstado;
    private final ObtenerCargaUseCase obtener;

    public CargaAcademicaController(ListarCargasPorAnioUseCase listar, CrearCargaUseCase crear,
            EditarCargaUseCase editar, CambiarEstadoCargaUseCase cambiarEstado, ObtenerCargaUseCase obtener) {
        this.listar = listar;
        this.crear = crear;
        this.editar = editar;
        this.cambiarEstado = cambiarEstado;
        this.obtener = obtener;
    }

    @GetMapping("/por-anio/{anioId}")
    public ResponseEntity<?> listar(@PathVariable UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(usuarioId, anioId));
        } catch (SecurityException e) { return forbidden(e); }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CargaAcademicaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CargaAcademicaResponseDto.desde(crear.ejecutar(usuarioId, req)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return conflict(e); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody CargaAcademicaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(CargaAcademicaResponseDto.desde(editar.ejecutar(usuarioId, id, req)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.finalizar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Carga finalizada.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.cancelar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Carga cancelada.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try { cambiarEstado.reactivar(usuarioId, id); return ResponseEntity.ok(new MensajeResponse("Carga reactivada.")); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(CargaAcademicaResponseDto.desde(obtener.ejecutar(usuarioId, id)));
        } catch (SecurityException e) { return forbidden(e); }
          catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage())); }
    }
    private ResponseEntity<?> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
    }
    private ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
    }

    record MensajeResponse(String mensaje) {}
}