package backend.config.infrastructure.rest.controller;

import backend.config.application.usecase.EditarMiInstitucionUseCase;
import backend.config.application.usecase.ObtenerMiInstitucionUseCase;
import backend.config.infrastructure.rest.dto.InstitucionConfigRequestDto;
import backend.config.infrastructure.rest.dto.InstitucionConfigResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/config/institucion")
public class InstitucionConfigController {

    private final ObtenerMiInstitucionUseCase obtener;
    private final EditarMiInstitucionUseCase editar;

    public InstitucionConfigController(ObtenerMiInstitucionUseCase obtener, EditarMiInstitucionUseCase editar) {
        this.obtener = obtener;
        this.editar = editar;
    }

    @GetMapping
    public ResponseEntity<?> obtener(@AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(InstitucionConfigResponseDto.desde(obtener.ejecutar(adminId)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@Valid @RequestBody InstitucionConfigRequestDto req,
            @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(InstitucionConfigResponseDto.desde(editar.ejecutar(adminId, req)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {
    }
}