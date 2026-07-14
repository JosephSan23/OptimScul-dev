package backend.config.infrastructure.rest.controller;

import backend.config.application.usecase.periodoAcademico.*;
import backend.config.infrastructure.rest.dto.PeriodoConfigRequestDto;
import backend.config.infrastructure.rest.dto.PeriodoConfigResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/config/periodos")
public class PeriodoController {

    private final ListarPeriodosUseCase listar;
    private final ObtenerPeriodoUseCase obtener;
    private final CrearPeriodoUseCase crear;
    private final EditarPeriodoUseCase editar;

    public PeriodoController(ListarPeriodosUseCase listar, ObtenerPeriodoUseCase obtener,
            CrearPeriodoUseCase crear, EditarPeriodoUseCase editar) {
        this.listar = listar;
        this.obtener = obtener;
        this.crear = crear;
        this.editar = editar;
    }

    @GetMapping("/por-anio/{anioId}")
    public ResponseEntity<?> listar(@PathVariable UUID anioId, @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity
                    .ok(listar.ejecutar(adminId, anioId).stream().map(PeriodoConfigResponseDto::desde).toList());
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping("/por-anio/{anioId}")
    public ResponseEntity<?> crear(@PathVariable UUID anioId, @Valid @RequestBody PeriodoConfigRequestDto req,
            @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(PeriodoConfigResponseDto.desde(crear.ejecutar(adminId, anioId, req)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return conflict(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(PeriodoConfigResponseDto.desde(obtener.ejecutar(adminId, id)));
        } catch (SecurityException e) {
            return forbidden(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody PeriodoConfigRequestDto req,
            @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(PeriodoConfigResponseDto.desde(editar.ejecutar(adminId, id, req)));
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