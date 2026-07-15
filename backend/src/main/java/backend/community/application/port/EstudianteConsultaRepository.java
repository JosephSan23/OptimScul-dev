package backend.community.application.port;

import java.util.List;
import java.util.UUID;

public interface EstudianteConsultaRepository {
    List<EstudianteResumen> listarPorInstitucion(UUID institucionId);
}
