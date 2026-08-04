package backend.notification.infrastructure.rest.controller;

import backend.notification.application.service.NotificacionService;
import backend.notification.application.NotificacionVista;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) { this.service = service; }

    @GetMapping
    public List<NotificacionVista> bandeja(@AuthenticationPrincipal UUID usuarioId) {
        return service.bandeja(usuarioId);
    }

    @GetMapping("/contador")
    public Map<String, Long> contador(@AuthenticationPrincipal UUID usuarioId) {
        return Map.of("noLeidas", service.noLeidas(usuarioId));
    }

    @PatchMapping("/{id}/leer")
    public void leer(@AuthenticationPrincipal UUID usuarioId, @PathVariable UUID id) {
        service.marcarLeida(id, usuarioId);
    }

    @PatchMapping("/leer-todas")
    public void leerTodas(@AuthenticationPrincipal UUID usuarioId) {
        service.marcarTodas(usuarioId);
    }

    @PatchMapping("/chat/{conversacionId}/leer")
    public void leerChat(@AuthenticationPrincipal UUID usuarioId, @PathVariable UUID conversacionId) {
        service.marcarLeidasDeConversacion(usuarioId, conversacionId);
    }
}