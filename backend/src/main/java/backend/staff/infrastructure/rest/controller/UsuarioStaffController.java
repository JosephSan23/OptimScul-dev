package backend.staff.infrastructure.rest.controller;

import backend.staff.application.usecase.CrearUsuarioStaffUseCase;
import backend.staff.application.usecase.ListarStaffUseCase;
import backend.staff.application.usecase.ObtenerStaffUseCase;
import backend.staff.application.usecase.EditarStaffUseCase;
import backend.staff.application.usecase.CambiarEstadoStaffUseCase;
import backend.staff.infrastructure.rest.dto.CrearUsuarioStaffRequestDto;
import backend.staff.infrastructure.rest.dto.EditarStaffRequestDto;
import backend.security.domain.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
public class UsuarioStaffController {

    private final CrearUsuarioStaffUseCase crearUsuarioStaffUseCase;
    private final ListarStaffUseCase listarStaffUseCase;
    private final ObtenerStaffUseCase obtenerStaffUseCase;
    private final EditarStaffUseCase editarStaffUseCase;
    private final CambiarEstadoStaffUseCase cambiarEstadoStaffUseCase;
    
    public UsuarioStaffController(CrearUsuarioStaffUseCase crearUsuarioStaffUseCase, ListarStaffUseCase listarStaffUseCase, ObtenerStaffUseCase obtenerStaffUseCase, EditarStaffUseCase editarStaffUseCase, CambiarEstadoStaffUseCase cambiarEstadoStaffUseCase) {
        this.crearUsuarioStaffUseCase = crearUsuarioStaffUseCase;
        this.listarStaffUseCase = listarStaffUseCase;
        this.obtenerStaffUseCase = obtenerStaffUseCase;
        this.editarStaffUseCase = editarStaffUseCase;
        this.cambiarEstadoStaffUseCase = cambiarEstadoStaffUseCase;
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearUsuarioStaffRequestDto request,
                                   @AuthenticationPrincipal UUID adminId) {
        try {
            Usuario creado = crearUsuarioStaffUseCase.ejecutar(
                    adminId, request.getRolCodigo(), request.getTipoDocumento(),
                    request.getNumeroDocumento(), request.getPrimerNombre(),
                    request.getPrimerApellido(), request.getCorreo());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MensajeResponse("Usuario creado. Usuario: " + creado.getUsername()
                            + ". Contraseña inicial: su número de documento."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    // suma al constructor: ListarStaffUseCase listarStaffUseCase  (y su campo/asignación)

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(listarStaffUseCase.ejecutar(adminId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    // Campos + constructor: suma ObtenerStaffUseCase y EditarStaffUseCase

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(obtenerStaffUseCase.ejecutar(adminId, id));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id,
                                    @Valid @RequestBody EditarStaffRequestDto request,
                                    @AuthenticationPrincipal UUID adminId) {
        try {
            editarStaffUseCase.ejecutar(adminId, id, request);
            return ResponseEntity.ok(new MensajeResponse("Usuario actualizado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try {
            cambiarEstadoStaffUseCase.activar(adminId, id);
            return ResponseEntity.ok(new MensajeResponse("Usuario activado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID adminId) {
        try {
            cambiarEstadoStaffUseCase.inactivar(adminId, id);
            return ResponseEntity.ok(new MensajeResponse("Usuario inactivado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {}
}