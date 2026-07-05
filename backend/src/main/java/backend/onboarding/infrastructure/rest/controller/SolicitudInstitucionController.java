package backend.onboarding.infrastructure.rest.controller;

import backend.onboarding.application.usecase.ListarSolicitudesUseCase;
import backend.onboarding.application.usecase.RecibirSolicitudUseCase;
import backend.onboarding.application.usecase.ListarMisSolicitudesUseCase;
import backend.onboarding.domain.model.SolicitudInstitucion;
import backend.onboarding.infrastructure.rest.dto.SolicitudInstitucionRequestDto;
import backend.onboarding.infrastructure.rest.dto.SolicitudInstitucionResponseDto;
import backend.onboarding.infrastructure.rest.mapper.SolicitudInstitucionRestMapper;
import backend.onboarding.application.usecase.AprobarSolicitudUseCase;
import backend.onboarding.application.usecase.RechazarSolicitudUseCase;
import backend.onboarding.infrastructure.rest.dto.RechazoRequestDto;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudInstitucionController {

    private final ListarSolicitudesUseCase listarSolicitudesUseCase;
    private final RecibirSolicitudUseCase recibirSolicitudUseCase;
    private final AprobarSolicitudUseCase aprobarSolicitudUseCase;
    private final RechazarSolicitudUseCase rechazarSolicitudUseCase;
    private final ListarMisSolicitudesUseCase listarMisSolicitudesUseCase;
    private final SolicitudInstitucionRestMapper restMapper;

    public SolicitudInstitucionController(ListarSolicitudesUseCase listarSolicitudesUseCase,
                                          RecibirSolicitudUseCase recibirSolicitudUseCase,
                                          AprobarSolicitudUseCase aprobarSolicitudUseCase,
                                          RechazarSolicitudUseCase rechazarSolicitudUseCase,
                                          ListarMisSolicitudesUseCase listarMisSolicitudesUseCase,
                                          SolicitudInstitucionRestMapper restMapper) {
        this.listarSolicitudesUseCase = listarSolicitudesUseCase;
        this.recibirSolicitudUseCase = recibirSolicitudUseCase;
        this.aprobarSolicitudUseCase = aprobarSolicitudUseCase;
        this.rechazarSolicitudUseCase = rechazarSolicitudUseCase;
        this.listarMisSolicitudesUseCase = listarMisSolicitudesUseCase;
        this.restMapper = restMapper;
    }

    @GetMapping
    public List<SolicitudInstitucionResponseDto> listar() {
        return listarSolicitudesUseCase.ejecutar().stream()
                .map(restMapper::toResponse)
                .toList();
    }

     @PostMapping
    public ResponseEntity<?> recibir(@Valid @RequestBody SolicitudInstitucionRequestDto request,
                                     @AuthenticationPrincipal UUID usuarioId) {
        try {
            SolicitudInstitucion creada = recibirSolicitudUseCase.ejecutar(restMapper.toDomain(request), usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(restMapper.toResponse(creada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        }
    }
    
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable UUID id,
                                     @AuthenticationPrincipal UUID usuarioId) {
        try {
            SolicitudInstitucion aprobada = aprobarSolicitudUseCase.ejecutar(id, usuarioId);
            return ResponseEntity.ok(restMapper.toResponse(aprobada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(@PathVariable UUID id,
                                      @Valid @RequestBody RechazoRequestDto request,
                                      @AuthenticationPrincipal UUID usuarioId) {
        try {
            SolicitudInstitucion rechazada = rechazarSolicitudUseCase.ejecutar(id, request.getMotivo(), usuarioId);
            return ResponseEntity.ok(restMapper.toResponse(rechazada));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/mias")
    public List<SolicitudInstitucionResponseDto> misSolicitudes(@AuthenticationPrincipal UUID usuarioId) {
        return listarMisSolicitudesUseCase.ejecutar(usuarioId).stream()
                .map(restMapper::toResponse)
                .toList();
    }

    record ErrorResponse(String mensaje) {}

}