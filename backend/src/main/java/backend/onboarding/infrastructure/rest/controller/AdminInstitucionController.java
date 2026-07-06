package backend.onboarding.infrastructure.rest.controller;

import backend.onboarding.application.usecase.CrearAdminInstitucionUseCase;
import backend.onboarding.application.usecase.ListarAdministradoresUseCase;
import backend.onboarding.infrastructure.rest.dto.CrearAdminInstitucionRequestDto;
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

    public AdminInstitucionController(CrearAdminInstitucionUseCase crearAdminInstitucionUseCase, ListarAdministradoresUseCase listarAdministradoresUseCase) {
        this.crearAdminInstitucionUseCase = crearAdminInstitucionUseCase;
        this.listarAdministradoresUseCase = listarAdministradoresUseCase;
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

    record MensajeResponse(String mensaje) {}
}