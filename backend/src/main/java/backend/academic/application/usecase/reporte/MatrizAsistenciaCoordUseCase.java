package backend.academic.application.usecase.reporte;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.domain.model.CargaAcademica;
import backend.security.application.AutorizacionService;
import backend.academic.application.service.MatrizAsistenciaService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class MatrizAsistenciaCoordUseCase {
    private final CargaAcademicaRepository cargaRepo;
    private final MatrizAsistenciaService matriz;
    private final AutorizacionService auth;

    public MatrizAsistenciaCoordUseCase(CargaAcademicaRepository cargaRepo, MatrizAsistenciaService matriz,
            AutorizacionService auth) {
        this.cargaRepo = cargaRepo;
        this.matriz = matriz;
        this.auth = auth;
    }

    public MatrizAsistenciaService.Matriz ejecutar(UUID usuarioId, UUID cargaId) {
        UUID inst = auth.institucionConRol(usuarioId, "COORDINADOR_ACADEMICO");
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!inst.equals(carga.getInstitucionId()))
            throw new SecurityException("Esa clase no pertenece a tu institución.");
        return matriz.construir(carga);
    }
}