package backend.people.infrastructure.rest.controller;

import backend.people.application.port.InstitucionRepository;
import backend.people.domain.model.Institucion;
import backend.security.application.AutorizacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Institución del usuario autenticado (nombre para la cabecera).
 * A diferencia de /api/config/institucion (solo ADMIN_INSTITUCION),
 * este endpoint sirve a CUALQUIER miembro: coordinador, docente, estudiante, acudiente.
 */
@RestController
@RequestMapping("/api/mi-institucion")
public class MiInstitucionController {

    private final InstitucionRepository institucionRepository;
    private final AutorizacionService autorizacion;

    public MiInstitucionController(InstitucionRepository institucionRepository, AutorizacionService autorizacion) {
        this.institucionRepository = institucionRepository;
        this.autorizacion = autorizacion;
    }

    @GetMapping
    public ResponseEntity<?> obtener(@AuthenticationPrincipal UUID usuarioId) {
        try {
            UUID inst = autorizacion.institucionActual(usuarioId);
            Institucion i = institucionRepository.findById(inst)
                    .orElseThrow(() -> new RuntimeException("Institución no encontrada."));
            return ResponseEntity.ok(new MiInstitucionResponse(i.getId(), i.getNombre(), i.getNombreCorto()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MiInstitucionResponse(UUID id, String nombre, String nombreCorto) {}
    record MensajeResponse(String mensaje) {}
}
