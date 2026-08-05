package backend.profile.infrastructure.rest.controller;

import backend.profile.application.service.PerfilService;
import backend.profile.application.PerfilVista;
import backend.profile.infrastructure.rest.dto.ActualizarPerfilRequest;
import backend.profile.infrastructure.rest.dto.CambiarPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) { this.service = service; }

    @GetMapping
    public PerfilVista obtener(@AuthenticationPrincipal UUID usuarioId) {
        return service.obtener(usuarioId);
    }

    @PutMapping
    public PerfilVista actualizar(@AuthenticationPrincipal UUID usuarioId,
                                  @Valid @RequestBody ActualizarPerfilRequest req) {
        return service.actualizar(usuarioId, req);
    }

    @PostMapping("/password")
    public void cambiarPassword(@AuthenticationPrincipal UUID usuarioId,
                                @Valid @RequestBody CambiarPasswordRequest req) {
        service.cambiarPassword(usuarioId, req);
    }

    @PostMapping(value = "/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> subirFoto(@AuthenticationPrincipal UUID usuarioId,
                                         @RequestParam("archivo") MultipartFile archivo) {
        return Map.of("fotoUrl", service.actualizarFoto(usuarioId, archivo));
    }
}