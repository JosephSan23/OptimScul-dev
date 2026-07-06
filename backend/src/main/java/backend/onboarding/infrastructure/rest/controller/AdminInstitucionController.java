package backend.onboarding.infrastructure.rest.controller;

import backend.onboarding.application.usecase.CrearAdminInstitucionUseCase;
import backend.onboarding.application.usecase.ListarAdministradoresUseCase;
import backend.onboarding.application.usecase.CambiarEstadoAdministradorUseCase;
import backend.onboarding.application.usecase.EditarAdministradorUseCase;
import backend.onboarding.infrastructure.rest.dto.CrearAdminInstitucionRequestDto;
import backend.onboarding.infrastructure.rest.dto.EditarAdministradorRequestDto;
import backend.security.domain.model.Usuario;
import backend.onboarding.application.port.AdministradorResumen;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdminInstitucionController {

    private final CrearAdminInstitucionUseCase crearAdminInstitucionUseCase;
    private final ListarAdministradoresUseCase listarAdministradoresUseCase;
    private final EditarAdministradorUseCase editarAdministradorUseCase;
    private final CambiarEstadoAdministradorUseCase cambiarEstadoAdministradorUseCase;

    public AdminInstitucionController(CrearAdminInstitucionUseCase crearAdminInstitucionUseCase, ListarAdministradoresUseCase listarAdministradoresUseCase, EditarAdministradorUseCase editarAdministradorUseCase, CambiarEstadoAdministradorUseCase cambiarEstadoAdministradorUseCase) {
        this.crearAdminInstitucionUseCase = crearAdminInstitucionUseCase;
        this.listarAdministradoresUseCase = listarAdministradoresUseCase;
        this.editarAdministradorUseCase = editarAdministradorUseCase;
        this.cambiarEstadoAdministradorUseCase = cambiarEstadoAdministradorUseCase;
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearAdminInstitucionRequestDto request,
                                   @AuthenticationPrincipal UUID superAdminId) {
        try {
            Usuario creado = crearAdminInstitucionUseCase.ejecutar(
                    superAdminId,                       // <-- NUEVO primer argumento
                    request.getTipoDocumento(),
                    request.getNumeroDocumento(),
                    request.getPrimerNombre(),
                    request.getPrimerApellido(),
                    request.getCorreo(),
                    request.getInstitucionId()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MensajeResponse(
                            "Administrador creado. Usuario: " + creado.getUsername()
                            + ". Contraseña inicial: su número de documento (deberá cambiarla al primer ingreso)."));
        } catch (SecurityException e) {                 // <-- NUEVO, va primero
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID superAdminId) {
        try {
            List<AdministradorResumen> lista = listarAdministradoresUseCase.ejecutar(superAdminId);
            return ResponseEntity.ok(lista);   // Spring serializa la proyección a JSON
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id,
                                     @AuthenticationPrincipal UUID superAdminId) {
        try {
            cambiarEstadoAdministradorUseCase.activar(superAdminId, id);
            return ResponseEntity.ok(new MensajeResponse("Administrador activado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id,
                                       @AuthenticationPrincipal UUID superAdminId) {
        try {
            cambiarEstadoAdministradorUseCase.inactivar(superAdminId, id);
            return ResponseEntity.ok(new MensajeResponse("Administrador inactivado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id,
                                    @Valid @RequestBody EditarAdministradorRequestDto request,
                                    @AuthenticationPrincipal UUID superAdminId) {
        try {
            editarAdministradorUseCase.ejecutar(superAdminId, id,
                    request.getTipoDocumento(), request.getNumeroDocumento(),
                    request.getPrimerNombre(), request.getPrimerApellido(),
                    request.getCorreo(), request.getInstitucionId());
            return ResponseEntity.ok(new MensajeResponse("Administrador actualizado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {}
}