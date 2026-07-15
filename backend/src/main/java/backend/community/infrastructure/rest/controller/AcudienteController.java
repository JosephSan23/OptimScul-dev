package backend.community.infrastructure.rest.controller;

import backend.community.application.usecase.AcudienteCrud.*;
import backend.community.infrastructure.rest.dto.AcudienteRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/community/estudiantes/{estudianteId}/acudientes")
public class AcudienteController {

    private final ListarAcudientesDeEstudianteUseCase listar;
    private final CrearVincularAcudienteUseCase crearVincular;

    public AcudienteController(ListarAcudientesDeEstudianteUseCase listar,
            CrearVincularAcudienteUseCase crearVincular) {
        this.listar = listar;
        this.crearVincular = crearVincular;
    }

    @GetMapping
    public ResponseEntity<?> listar(@PathVariable UUID estudianteId, @AuthenticationPrincipal UUID coordId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(coordId, estudianteId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearVincular(@PathVariable UUID estudianteId, @Valid @RequestBody AcudienteRequestDto req,
            @AuthenticationPrincipal UUID coordId) {
        try {
            CrearVincularAcudienteUseCase.Resultado r = crearVincular.ejecutar(coordId, estudianteId, req);
            String msg = r.reutilizado()
                    ? "Acudiente existente vinculado al estudiante."
                    : "Acudiente creado y vinculado. Usuario: " + r.username() + " · Contraseña: su documento.";
            return ResponseEntity.status(HttpStatus.CREATED).body(new MensajeResponse(msg));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {
    }
}