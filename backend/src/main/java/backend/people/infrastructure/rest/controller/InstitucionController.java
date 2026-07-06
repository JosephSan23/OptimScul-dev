package backend.people.infrastructure.rest.controller;

import backend.people.application.usecase.CambiarEstadoInstitucionUseCase;
import backend.people.application.usecase.CrearInstitucionUseCase;
import backend.people.application.usecase.EditarInstitucionUseCase;
import backend.people.application.usecase.ListarInstitucionesUseCase;
import backend.people.domain.model.Institucion;
import backend.people.infrastructure.rest.dto.InstitucionRequestDto;
import backend.people.infrastructure.rest.dto.InstitucionResponseDto;
import backend.people.infrastructure.rest.mapper.InstitucionRestMapper;
import backend.security.application.AutorizacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/instituciones")
public class InstitucionController {

    private final ListarInstitucionesUseCase listarInstitucionesUseCase;
    private final CrearInstitucionUseCase crearInstitucionUseCase;
    private final EditarInstitucionUseCase editarInstitucionUseCase;
    private final CambiarEstadoInstitucionUseCase cambiarEstadoInstitucionUseCase;
    private final InstitucionRestMapper restMapper;
    private final AutorizacionService autorizacionService;

    public InstitucionController(ListarInstitucionesUseCase listarInstitucionesUseCase,
                                 CrearInstitucionUseCase crearInstitucionUseCase,
                                 EditarInstitucionUseCase editarInstitucionUseCase,
                                 CambiarEstadoInstitucionUseCase cambiarEstadoInstitucionUseCase,
                                 InstitucionRestMapper restMapper,
                                 AutorizacionService autorizacionService) {
        this.listarInstitucionesUseCase = listarInstitucionesUseCase;
        this.crearInstitucionUseCase = crearInstitucionUseCase;
        this.editarInstitucionUseCase = editarInstitucionUseCase;
        this.cambiarEstadoInstitucionUseCase = cambiarEstadoInstitucionUseCase;
        this.restMapper = restMapper;
        this.autorizacionService = autorizacionService;
    }

    @GetMapping
    public List<InstitucionResponseDto> listar() {
        return listarInstitucionesUseCase.ejecutar().stream().map(restMapper::toResponse).toList();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody InstitucionRequestDto request,
                                   @AuthenticationPrincipal UUID usuarioId) {
        try {
            autorizacionService.exigirSuperAdmin(usuarioId);
            Institucion creada = crearInstitucionUseCase.ejecutar(restMapper.toDomain(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(restMapper.toResponse(creada));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody InstitucionRequestDto request,
                                    @AuthenticationPrincipal UUID usuarioId) {
        try {
            autorizacionService.exigirSuperAdmin(usuarioId);
            Institucion actualizada = editarInstitucionUseCase.ejecutar(id, restMapper.toDomain(request));
            return ResponseEntity.ok(restMapper.toResponse(actualizada));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/suspender")
    public ResponseEntity<?> suspender(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            autorizacionService.exigirSuperAdmin(usuarioId);
            return ResponseEntity.ok(restMapper.toResponse(cambiarEstadoInstitucionUseCase.suspender(id)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            autorizacionService.exigirSuperAdmin(usuarioId);
            return ResponseEntity.ok(restMapper.toResponse(cambiarEstadoInstitucionUseCase.activar(id)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID usuarioId) {
        try {
            autorizacionService.exigirSuperAdmin(usuarioId);
            return ResponseEntity.ok(restMapper.toResponse(cambiarEstadoInstitucionUseCase.reactivar(id)));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    record ErrorResponse(String mensaje) {}
}