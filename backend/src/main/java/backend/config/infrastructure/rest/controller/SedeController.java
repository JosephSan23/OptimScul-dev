package backend.config.infrastructure.rest.controller;

import backend.config.application.usecase.sede.*;
import backend.config.infrastructure.rest.dto.SedeRequestDto;
import backend.config.infrastructure.rest.dto.SedeResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/config/sedes")
public class SedeController {

    private final ListarSedesUseCase listar;
    private final CrearSedeUseCase crear;
    private final EditarSedeUseCase editar;
    private final ObtenerSedeUseCase obtener;
    private final CambiarEstadoSedeUseCase estado;

    public SedeController(ListarSedesUseCase listar, CrearSedeUseCase crear,
                          EditarSedeUseCase editar, ObtenerSedeUseCase obtener, CambiarEstadoSedeUseCase estado) {
        this.listar = listar; 
        this.crear = crear; 
        this.editar = editar;
        this.obtener = obtener;
        this.estado = estado;
    }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID adminId) {
        try {
            List<SedeResponseDto> r = listar.ejecutar(adminId).stream().map(SedeResponseDto::desde).toList();
            return ResponseEntity.ok(r);
        } catch (SecurityException e) { return forbidden(e); }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody SedeRequestDto req, @AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(SedeResponseDto.desde(crear.ejecutar(adminId, req))); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody SedeRequestDto req,
                                    @AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.ok(SedeResponseDto.desde(editar.ejecutar(adminId, id, req))); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try { estado.activar(adminId, id); return ResponseEntity.ok(new MensajeResponse("Sede activada.")); }
        catch (SecurityException e) { return forbidden(e); } catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try { estado.inactivar(adminId, id); return ResponseEntity.ok(new MensajeResponse("Sede inactivada.")); }
        catch (SecurityException e) { return forbidden(e); } catch (RuntimeException e) { return conflict(e); }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.ok(SedeResponseDto.desde(obtener.ejecutar(adminId, id))); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage())); }
    }

    private ResponseEntity<?> forbidden(Exception e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage())); }
    private ResponseEntity<?> conflict(Exception e)  { return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage())); }

    record MensajeResponse(String mensaje) {}
}