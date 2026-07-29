package backend.academic.infrastructure.rest.controller.rolacademico;

import backend.academic.application.usecase.actividad.entrega.ObtenerMiEntregaUseCase;
import backend.academic.application.usecase.actividad.entrega.SubirEntregaUseCase;
import backend.academic.application.usecase.actividad.estudiante.ListarMisActividadesUseCase;
import backend.academic.application.usecase.actividad.entrega.EliminarArchivoEntregaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estudiante/actividades")
public class EstudianteEntregaController {

    private final SubirEntregaUseCase subir;
    private final ObtenerMiEntregaUseCase obtener;
    private final ListarMisActividadesUseCase listar;
    private final EliminarArchivoEntregaUseCase eliminar;

    public EstudianteEntregaController(SubirEntregaUseCase subir, ObtenerMiEntregaUseCase obtener,
            ListarMisActividadesUseCase listar, EliminarArchivoEntregaUseCase eliminar) {
        this.subir = subir;
        this.obtener = obtener;
        this.listar = listar;
        this.eliminar = eliminar;
    }

    @GetMapping
    public ResponseEntity<?> misActividades(@RequestParam UUID anioId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listar.ejecutar(usuarioId, anioId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @PostMapping(value = "/{actividadId}/entrega", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> entregar(
            @PathVariable UUID actividadId,
            @RequestParam(value = "comentario", required = false) String comentario,
            @RequestParam(value = "archivos", required = false) MultipartFile[] archivos,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            List<SubirEntregaUseCase.ArchivoEntrada> lista = new ArrayList<>();
            if (archivos != null) {
                for (MultipartFile mf : archivos) {
                    if (mf == null || mf.isEmpty()) continue;
                    lista.add(new SubirEntregaUseCase.ArchivoEntrada(
                            mf.getOriginalFilename(), mf.getContentType(), mf.getSize(), mf.getInputStream()));
                }
            }
            if (lista.isEmpty() && (comentario == null || comentario.isBlank()))
                return ResponseEntity.badRequest().body(new Msg("Debes adjuntar al menos un archivo o un comentario."));

            return ResponseEntity.ok(subir.ejecutar(usuarioId, actividadId, comentario, lista));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new Msg("No se pudo leer el archivo: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/{actividadId}/entrega")
    public ResponseEntity<?> miEntrega(@PathVariable UUID actividadId, @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(obtener.ejecutar(usuarioId, actividadId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @DeleteMapping("/{actividadId}/entrega/documentos/{documentoId}")
    public ResponseEntity<?> eliminarArchivo(@PathVariable UUID actividadId, @PathVariable UUID documentoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(eliminar.ejecutar(usuarioId, actividadId, documentoId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    record Msg(String mensaje) {}
}