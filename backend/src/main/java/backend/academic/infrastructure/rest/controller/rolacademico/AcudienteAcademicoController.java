package backend.academic.infrastructure.rest.controller.rolacademico;

import backend.academic.application.usecase.acudiente.ListarHijosUseCase;
import backend.academic.application.usecase.acudiente.NotasHijoUseCase;
import backend.academic.application.service.HorarioFamiliaService;
import backend.academic.application.service.AsistenciaFamiliaService;
import backend.academic.application.service.ContextoAcudienteService;
import backend.people.application.port.EstudianteAcudienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/acudiente")
public class AcudienteAcademicoController {

    private final ListarHijosUseCase listarHijos;
    private final NotasHijoUseCase notasHijo;
    private final ContextoAcudienteService contexto;
    private final HorarioFamiliaService horarioFamilia;
    private final AsistenciaFamiliaService asistenciaFamilia;
    private final EstudianteAcudienteRepository vinculoRepo;

    public AcudienteAcademicoController(ListarHijosUseCase listarHijos, NotasHijoUseCase notasHijo, 
            ContextoAcudienteService contexto, EstudianteAcudienteRepository vinculoRepo, HorarioFamiliaService horarioFamilia, AsistenciaFamiliaService asistenciaFamilia) {
        this.listarHijos = listarHijos;
        this.notasHijo = notasHijo;
        this.contexto = contexto;
        this.vinculoRepo = vinculoRepo;
        this.horarioFamilia = horarioFamilia;
        this.asistenciaFamilia = asistenciaFamilia;
    }

    @GetMapping("/hijos")
    public ResponseEntity<?> hijos(@AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(listarHijos.ejecutar(usuarioId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/hijos/{estudianteId}/notas")
    public ResponseEntity<?> notasHijo(@PathVariable UUID estudianteId,
            @RequestParam UUID anioId, @RequestParam UUID periodoId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            return ResponseEntity.ok(notasHijo.ejecutar(usuarioId, estudianteId, anioId, periodoId));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(e.getMessage()));
        }
    }

    @GetMapping("/hijos/{estudianteId}/horario")
    public ResponseEntity<?> horarioHijo(@PathVariable UUID estudianteId, @RequestParam UUID anioId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            var ctx = contexto.resolver(usuarioId);
            if (!vinculoRepo.existsByEstudianteIdAndAcudienteId(estudianteId, ctx.acudienteId()))
                throw new SecurityException("Ese estudiante no está vinculado a ti.");
            return ResponseEntity.ok(horarioFamilia.calcular(estudianteId, anioId));
        } catch (SecurityException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage())); }
    }

    @GetMapping("/hijos/{estudianteId}/asistencia")
    public ResponseEntity<?> asistenciaHijo(@PathVariable UUID estudianteId, @RequestParam UUID anioId,
            @AuthenticationPrincipal UUID usuarioId) {
        try {
            var ctx = contexto.resolver(usuarioId);
            if (!vinculoRepo.existsByEstudianteIdAndAcudienteId(estudianteId, ctx.acudienteId()))
                throw new SecurityException("Ese estudiante no está vinculado a ti.");
            return ResponseEntity.ok(asistenciaFamilia.calcular(estudianteId, anioId));
        } catch (SecurityException e) { return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Msg(e.getMessage())); }
    }

    record Msg(String mensaje) {
    }
}