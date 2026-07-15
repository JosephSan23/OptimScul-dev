package backend.community.application.usecase.AcudienteCrud;

import backend.community.application.port.AcudienteConsultaRepository;
import backend.community.application.port.AcudienteDeEstudiante;
import backend.people.application.port.EstudianteRepository;
import backend.people.domain.model.Estudiante;
import backend.security.application.AutorizacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ListarAcudientesDeEstudianteUseCase {
    private final AcudienteConsultaRepository consulta;
    private final EstudianteRepository estudianteRepository;
    private final AutorizacionService auth;

    public ListarAcudientesDeEstudianteUseCase(AcudienteConsultaRepository consulta,
            EstudianteRepository estudianteRepository, AutorizacionService auth) {
        this.consulta = consulta;
        this.estudianteRepository = estudianteRepository;
        this.auth = auth;
    }

    public List<AcudienteDeEstudiante> ejecutar(UUID coordId, UUID estudianteId) {
        UUID inst = auth.institucionConRol(coordId, "COORDINADOR_ACADEMICO");
        Estudiante e = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El estudiante no existe."));
        if (!inst.equals(e.getInstitucionId()))
            throw new SecurityException("El estudiante es de otra institución.");
        return consulta.listarPorEstudiante(estudianteId);
    }
}