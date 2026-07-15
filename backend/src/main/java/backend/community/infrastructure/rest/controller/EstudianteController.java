package backend.community.infrastructure.rest.controller;

import backend.community.application.usecase.EstudianteCrud.CambiarEstadoEstudianteUseCase;
import backend.community.application.usecase.EstudianteCrud.CrearEstudianteUseCase;
import backend.community.application.usecase.EstudianteCrud.EditarEstudianteUseCase;
import backend.community.application.usecase.EstudianteCrud.ListarEstudiantesUseCase;
import backend.community.application.usecase.EstudianteCrud.ObtenerEstudianteUseCase;
import backend.community.infrastructure.rest.dto.EditarEstudianteRequestDto;
import backend.community.infrastructure.rest.dto.EstudianteRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/community/estudiantes")
public class EstudianteController {

    private final ListarEstudiantesUseCase listar;
    private final CrearEstudianteUseCase crear;
    private final ObtenerEstudianteUseCase obtenerEstudiante;
    private final EditarEstudianteUseCase editarEstudiante;
    private final CambiarEstadoEstudianteUseCase cambiarEstado;

    public EstudianteController(ListarEstudiantesUseCase listar, CrearEstudianteUseCase crear,
            ObtenerEstudianteUseCase obtenerEstudiante, EditarEstudianteUseCase editarEstudiante,
            CambiarEstadoEstudianteUseCase cambiarEstado) {
        this.listar = listar;
        this.crear = crear;
        this.obtenerEstudiante = obtenerEstudiante;
        this.editarEstudiante = editarEstudiante;
        this.cambiarEstado = cambiarEstado;
    }

    @GetMapping
    public ResponseEntity<?> listar(@AuthenticationPrincipal UUID adminId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(adminId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EstudianteRequestDto req,
            @AuthenticationPrincipal UUID adminId) {
        try {
            CrearEstudianteUseCase.Resultado r = crear.ejecutar(adminId, req);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MensajeResponse(
                    "Estudiante creado. Usuario: " + r.username()
                            + " · Código: " + r.codigoEstudiante() + " · Contraseña: su número de documento."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable UUID id, @AuthenticationPrincipal UUID coordId) {
        try {
            return ResponseEntity.ok(obtenerEstudiante.ejecutar(coordId, id));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable UUID id, @Valid @RequestBody EditarEstudianteRequestDto req,
            @AuthenticationPrincipal UUID coordId) {
        try {
            editarEstudiante.ejecutar(coordId, id, req);
            return ResponseEntity.ok(new MensajeResponse("Estudiante actualizado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable UUID id, @AuthenticationPrincipal UUID coordId) {
        try {
            cambiarEstado.activar(coordId, id);
            return ResponseEntity.ok(new MensajeResponse("Estudiante activado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable UUID id, @AuthenticationPrincipal UUID coordId) {
        try {
            cambiarEstado.inactivar(coordId, id);
            return ResponseEntity.ok(new MensajeResponse("Estudiante inactivado."));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensajeResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse(e.getMessage()));
        }
    }

    record MensajeResponse(String mensaje) {
    }
}