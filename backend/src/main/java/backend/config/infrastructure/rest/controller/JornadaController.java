package backend.config.infrastructure.rest.controller;

import backend.config.application.usecase.jornada.*;
import backend.config.infrastructure.rest.dto.JornadaRequestDto;
import backend.config.infrastructure.rest.dto.JornadaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/config/jornadas")
public class JornadaController {

    private final ListarJornadasUseCase listar;
    private final ObtenerJornadaUseCase obtener;
    private final CrearJornadaUseCase crear;
    private final EditarJornadaUseCase editar;
    private final CambiarEstadoJornadaUseCase estado;

    public JornadaController(ListarJornadasUseCase listar, ObtenerJornadaUseCase obtener, CrearJornadaUseCase crear,
                             EditarJornadaUseCase editar, CambiarEstadoJornadaUseCase estado) {
        this.listar = listar; this.obtener = obtener; this.crear = crear; this.editar = editar; this.estado = estado;
    }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.ok(listar.ejecutar(adminId).stream().map(JornadaResponseDto::desde).toList()); }
        catch (SecurityException e) { return forbidden(e); }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.ok(JornadaResponseDto.desde(obtener.ejecutar(adminId, id))); }
        catch (SecurityException e) { return forbidden(e); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage())); }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody JornadaRequestDto req, @AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(JornadaResponseDto.desde(crear.ejecutar(adminId, req))); }
        catch (SecurityException e) { return forbidden(e); } catch (RuntimeException e) { return conflict(e); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody JornadaRequestDto req,
                                    @AuthenticationPrincipal UUID adminId) {
        try { return ResponseEntity.ok(JornadaResponseDto.desde(editar.ejecutar(adminId, id, req))); }
        catch (SecurityException e) { return forbidden(e); } catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try { estado.activar(adminId, id); return ResponseEntity.ok(new MensajeResponse("Jornada activada.")); }
        catch (SecurityException e) { return forbidden(e); } catch (RuntimeException e) { return conflict(e); }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try { estado.inactivar(adminId, id); return ResponseEntity.ok(new MensajeResponse("Jornada inactivada.")); }
        catch (SecurityException e) { return forbidden(e); } catch (RuntimeException e) { return conflict(e); }
    }

    private ResponseEntity<?> forbidden(Exception e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage())); }
    private ResponseEntity<?> conflict(Exception e)  { return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage())); }

    record MensajeResponse(String mensaje) {}
}