package backend.onboarding.infrastructure.rest.controller;

import backend.onboarding.application.usecase.RegistrarVisitanteUseCase;
import backend.onboarding.infrastructure.rest.dto.RegistroRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/registro")
public class RegistroController {

    private final RegistrarVisitanteUseCase registrarVisitanteUseCase;

    public RegistroController(RegistrarVisitanteUseCase registrarVisitanteUseCase) {
        this.registrarVisitanteUseCase = registrarVisitanteUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequestDto request) {
        try {
            registrarVisitanteUseCase.ejecutar(
                    request.getTipoDocumento(),
                    request.getNumeroDocumento(),
                    request.getPrimerNombre(),
                    request.getPrimerApellido(),
                    request.getCorreo(),
                    request.getPassword()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MensajeResponse("Cuenta creada correctamente. Ya puedes iniciar sesión."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {}
}