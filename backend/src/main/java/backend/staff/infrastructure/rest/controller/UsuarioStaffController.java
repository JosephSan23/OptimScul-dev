package backend.staff.infrastructure.rest.controller;

import backend.staff.application.usecase.CrearUsuarioStaffUseCase;
import backend.staff.application.usecase.ListarStaffUseCase;
import backend.staff.infrastructure.rest.dto.CrearUsuarioStaffRequestDto;
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

    public UsuarioStaffController(CrearUsuarioStaffUseCase crearUsuarioStaffUseCase, ListarStaffUseCase listarStaffUseCase) {
        this.crearUsuarioStaffUseCase = crearUsuarioStaffUseCase;
        this.listarStaffUseCase = listarStaffUseCase;
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

    record MensajeResponse(String mensaje) {}
}