package backend.academic.application.usecase.docente;

import backend.academic.application.port.CargaAcademica.CargaAcademicaRepository;
import backend.academic.application.port.DocenteConsultaRepository;
import backend.academic.application.port.Asistencia.ReporteAsistenciaFila;
import backend.academic.application.service.ContextoDocenteService;
import backend.academic.domain.model.CargaAcademica;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ReporteAsistenciaUseCase {
    private final DocenteConsultaRepository consulta;
    private final CargaAcademicaRepository cargaRepo;
    private final ContextoDocenteService contexto;

    public ReporteAsistenciaUseCase(DocenteConsultaRepository consulta, CargaAcademicaRepository cargaRepo,
                                    ContextoDocenteService contexto) {
        this.consulta = consulta; this.cargaRepo = cargaRepo; this.contexto = contexto;
    }

    public List<ReporteAsistenciaFila> ejecutar(UUID usuarioId, UUID cargaId) {
        var ctx = contexto.resolver(usuarioId);
        CargaAcademica carga = cargaRepo.findById(cargaId)
                .orElseThrow(() -> new RuntimeException("La clase no existe."));
        if (!ctx.profesorId().equals(carga.getProfesorId()))
            throw new SecurityException("Esa clase no te pertenece.");
        return consulta.reporteAsistencia(cargaId);
    }
}