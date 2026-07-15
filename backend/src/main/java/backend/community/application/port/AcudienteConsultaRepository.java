package backend.community.application.port;

import java.util.List;
import java.util.UUID;

public interface AcudienteConsultaRepository {
    List<AcudienteDeEstudiante> listarPorEstudiante(UUID estudianteId);
}