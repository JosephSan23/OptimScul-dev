package backend.academic.application.service;

import backend.academic.application.port.Horario.HorarioResumen;
import backend.academic.application.port.Asistencia.PortalFamiliaConsultaRepository;
import backend.enrollment.application.port.MatriculaRepository;
import backend.enrollment.domain.model.Matricula;
import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.Estudiante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HorarioFamiliaService {
    public record Vista(boolean matriculado, List<HorarioResumen> franjas) {
    }

    private final EstudianteRepository estudianteRepo;
    private final MatriculaRepository matriculaRepo;
    private final PortalFamiliaConsultaRepository portalRepo;

    public HorarioFamiliaService(EstudianteRepository estudianteRepo, MatriculaRepository matriculaRepo,
            PortalFamiliaConsultaRepository portalRepo) {
        this.estudianteRepo = estudianteRepo;
        this.matriculaRepo = matriculaRepo;
        this.portalRepo = portalRepo;
    }

    public Vista calcular(UUID estudianteId, UUID anioId) {
        Estudiante est = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        Matricula mat = matriculaRepo
                .findByInstitucionIdAndEstudianteIdAndAnioLectivoId(est.getInstitucionId(), estudianteId, anioId)
                .orElse(null);
        if (mat == null || mat.getGrupoId() == null)
            return new Vista(false, List.of());
        return new Vista(true, portalRepo.horarioDeGrupo(mat.getGrupoId(), anioId));
    }
}