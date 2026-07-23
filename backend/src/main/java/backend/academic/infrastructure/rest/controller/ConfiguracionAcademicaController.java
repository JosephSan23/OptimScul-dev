package backend.academic.infrastructure.rest.controller;

import backend.academic.application.usecase.configAcademica.*;
import backend.academic.infrastructure.rest.dto.ConfiguracionAcademicaRequestDto;
import backend.academic.infrastructure.rest.dto.ConfiguracionAcademicaResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/academico/configuracion")
public class ConfiguracionAcademicaController {

    private final ObtenerConfiguracionUseCase obtener;
    private final GuardarConfiguracionUseCase guardar;

    public ConfiguracionAcademicaController(ObtenerConfiguracionUseCase obtener, GuardarConfiguracionUseCase guardar) {
        this.obtener = obtener;
        this.guardar = guardar;
    }

    @GetMapping
    public ResponseEntity<?> obtener(@AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(ConfiguracionAcademicaResponseDto.desde(obtener.ejecutar(usuarioId)));
        } catch (SecurityException e) {
            return forbidden(e);
        }
    }

    @PutMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ConfiguracionAcademicaRequestDto req,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(ConfiguracionAcademicaResponseDto.desde(guardar.ejecutar(usuarioId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    private ResponseEntity<?> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
    }

    private ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Msg(e.getMessage()));
    }

    record Msg(String mensaje) {
    }
}