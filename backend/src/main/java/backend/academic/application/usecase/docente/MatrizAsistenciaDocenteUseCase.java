package backend.academic.application.usecase.docente;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.service.MatrizAsistenciaService;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.CargaAcademica;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class MatrizAsistenciaDocenteUseCase {
    private final CargaAcademicaRepository cargaRepo;
    private final MatrizAsistenciaService matriz;
    private final ContextoDocenteService contexto;

    public MatrizAsistenciaDocenteUseCase(CargaAcademicaRepository cargaRepo, MatrizAsistenciaService matriz,
            ContextoDocenteService contexto) {
        this.cargaRepo = cargaRepo;
        this.matriz = matriz;
        this.contexto = contexto;
    }

    public MatrizAsistenciaService.Matriz ejecutar(UUID usuarioId, UUID cargaId) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");
        return matriz.construir(carga);
    }
}