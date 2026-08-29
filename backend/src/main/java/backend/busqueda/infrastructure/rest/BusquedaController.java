package backend.busqueda.infrastructure.rest;

import backend.busqueda.application.BusquedaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/busqueda")
public class BusquedaController {

    private final BusquedaService busqueda;

    public BusquedaController(BusquedaService busqueda) {
        this.busqueda = busqueda;
    }

    @GetMapping
    public ResponseEntity<?> buscar(@RequestParam(value = "q", required = false) String q,
                                    @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(busqueda.buscar(usuarioId, q));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {}
}
